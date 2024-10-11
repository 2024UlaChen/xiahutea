// MODAL

$(".orderinfo").on('click', function () {
    $("#myModal").fadeIn();
});

$(".infoClose").on("click", function () {
    $("#myModal").fadeOut();
})

// 常見問題
$("div.question").on("click", function () {
    location.href = "faq.html"
});
// 取店家資訊
const urlParams = new URLSearchParams(window.location.search);
const storeId = urlParams.get('storeId'); // 这里获取的是 'storeId' 参数的值
const storeIdNum = parseInt(storeId); // 将字符串转换为数字

const storeSimpleInfo = document.querySelector(".storeSimpleInfo");
const deliveryOptions = document.querySelector("#deliveryOptions");
const adImg = document.getElementById('adImage');
const productImage = document.getElementById('productImage');
const totalAmt = document.getElementById("totalAmt");
const buyNumInput = document.getElementById("buyNum");
let targetProductId;
let storedSize;
let storedTemperature;
let storedSweetness;
let storedToppings;

//
document.querySelector('.close').onclick = function () {
    document.getElementById("lightbox").style.display = "none";
};


//
function getStoreAdImg(storeIdNum) {
    adImg.src = '';
    fetch(`products/advertise/image/${storeIdNum}`)
        .then(response => {
            if (response.ok) {
                return response.text(); // 將回應解析為文本，也就是Base64字符串
            } else {
                throw new Error('Image not found');
            }
        })
        .then(base64Image => {
            // 將Base64圖片插入到img標籤中
            adImg.src = `data:image/png;base64,${base64Image}`;
        })
        .catch(error => {
            console.error('Error fetching the image:', error);
        });
}


function getStoreInfo(storeIdNum) {
    if (isNaN(storeIdNum)) {
        console.error('无效的店家 ID'); // 处理无效的店家 ID
    } else {
        // Fetch 店家信息
        fetch(`/store/getInfo/${storeIdNum}`) // 根据你的后端 API 结构调整路径
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(store => {
                console.log('店家信息:', store);
                let storeSimpleInfoData = "";
                storeSimpleInfoData += `
                    <h4 class="storeName">${store.storeName}</h4>
                    <div class="rating">
                        <span id="storeRating"></span>
                    </div>
                    <div class="hours">
                        <i class="fa-solid fa-business-time"></i>
                        <span id="openHours">${store.openingHours}</span>~<span id="closingHours">${store.closingHours}</span>
                    </div>
                    <div class="phone">
                        <i class="fa-solid fa-phone"></i><span id="storePhone">${store.storePhone}</span>
                    </div>
                    <div class="address">
                        <i class="fa-sharp-duotone fa-solid fa-map-location-dot"></i>
                        <span class="storeAddress">${store.storeAddress}</span>
                    </div>
                `;
                storeSimpleInfo.innerHTML = storeSimpleInfoData;
                document.querySelector('.modalStoreName').textContent = store.storeName;
                document.querySelector('span.score').textContent = store.score;
                document.querySelector('.open').textContent = store.openingHours;
                document.querySelector('.closing').textContent = store.closingHours;
                document.querySelector('span.phone').textContent = store.storePhone;
                document.querySelector('span.address').textContent = store.storeAddress;
                document.querySelector('.vat').textContent = store.vat;
                let deliveryBlock = "";
                let deliveryText = "";
                if (store.isDelivery) {
                    deliveryBlock += `
                        <h5>訂購說明</h5>
                        <div class="section-content">
                            滿 <span class="delivery">${store.deliveryMoney}</span>可外送
                        </div>
                    `;
                    deliveryText = `<strong>此商品可提供外送服務</strong>`;
                } else {
                    deliveryText = `<strong>此商品不提供外送服務</strong>`;
                }
                deliveryBlock += `
                    <h5>取貨方式</h5>
                    <div class="section-content">
                        <span class="isdelivery">${deliveryText}</span><br>
                        營業期間都可下單
                    </div>
                `;
                deliveryOptions.innerHTML = deliveryBlock;
            })
            .catch(error => {
                console.error('Error fetching store information:', error);
            });
    }
}

document.addEventListener("DOMContentLoaded", function () {
    getStoreInfo(storeIdNum);
    getStoreAdImg(storeIdNum);
    fetchProductsAndCategories(storeIdNum); // 调用函数以获取产品和类别信息
})

// 兩個切換按鈕的按鈕
var $tabs = $('.modal-tabs div');
var $sections = $('.modal-section');

$tabs.on('click', function () {
    $tabs.removeClass('active');
    $sections.removeClass('active');

    var index = $(this).index();

    $(this).addClass('active');
    $sections.eq(index).addClass('active');
});

// TODO 為什麼不回傳only上架的類 & 商品就好?
function fetchProductsAndCategories(storeId) {
    fetch(`/categories/store/${storeId}`)
        .then(response => response.json())
        .then(categories => {
            // 遍歷每個分類，獲取相應的產品
            categories.forEach((category) => {
                if (category.categoryStatus) { // 如果分类状态为'下架'
                    fetch(`/products/byCategory?ProductCategoryId=${category.categoryId}`)
                        .then(response => response.json())
                        .then((products) => {
                            if (products.length !== 0) {
                                displayProducts(category.categoryName, products);
                            }
                        })
                        .catch(error => {
                            console.error('Error fetching products:', error);
                        });
                }
            });
        })
        .catch(error => {
            console.error('Error fetching categories:', error);
        });
}

// 顯示產品的函數
function displayProducts(categoryName, products) {
    const menuContainer = document.querySelector('.menu-container');
    // 創建 menu section
    const section = document.createElement('div');
    section.classList.add('menu-section');
    // 添加 h2 標籤
    const h2 = document.createElement('h2');
    h2.textContent = categoryName;
    section.appendChild(h2);
    // 創建 ul 元素
    const ul = document.createElement('ul');
    ul.classList.add('menulist');
    // 迭代 products 數組

    products.forEach(product => {
        // 創建 li 元素
        // 判斷商品上架的時候
        if (product.productStatus) {
            const li = document.createElement('li');
            li.classList.add("menuLi");
            li.dataset.productId = product.productId;
            // 添加商品名稱
            const nameSpan = document.createElement('span');
            nameSpan.textContent = `${product.size} ● ${product.productName}`;
            nameSpan.classList.add("menu");

            li.appendChild(nameSpan);

            // 添加商品價格
            const priceSpan = document.createElement('span');
            priceSpan.textContent = product.productPrice;
            priceSpan.classList.add("menu");

            li.appendChild(priceSpan);
            // 將 li 添加到 ul 中
            ul.appendChild(li);
        }
    });
    // 將 ul 添加到 section 中
    section.appendChild(ul);
    // 將 section 添加到 container 中
    menuContainer.appendChild(section);
}


function fetchProductImage(productId) {
    // Construct the URL for the image based on the product ID
    productImage.src = '';

    // Use fetch to get the image
    fetch(`/products/${productId}/image`)
        .then(response => {
            if (response.ok) {
                return response.text(); // 將回應解析為文本，也就是Base64字符串
            } else {
                throw new Error('Image not found');
            }
        })
        .then(base64Image => {
            // 將Base64圖片插入到img標籤中
            productImage.src = `data:image/png;base64,${base64Image}`;
        })
        .catch(error => {
            console.error('Error fetching the image:', error);
        });
}

function addOption(container, condition, text, type) {
    if (condition) {
        const div = document.createElement("div");
        div.classList.add(type);  // 使用不同的類名來區分選項類型
        div.textContent = text;
        container.appendChild(div);
    }
}

function setModalDetail(product) {
    // 模態框的選項設置
    const temperatureOptions = document.getElementById("temperatureOptions");
    const sweetnessOptions = document.getElementById("sweetnessOptions");
    const toppingsOptions = document.getElementById("toppingsOptions");

    // 清空之前的內容
    temperatureOptions.innerHTML = '';
    sweetnessOptions.innerHTML = '';
    toppingsOptions.innerHTML = '';

    // 溫度選項映射
    addOption(temperatureOptions, product.normalIce, "正常冰", "temperature");
    addOption(temperatureOptions, product.lessIce, "少冰", "temperature");
    addOption(temperatureOptions, product.iceFree, "去冰", "temperature");
    addOption(temperatureOptions, product.lightIce, "微冰", "temperature");
    addOption(temperatureOptions, product.roomTemperature, "常溫", "temperature");
    addOption(temperatureOptions, product.hot, "熱", "temperature");

    // 甜度選項映射
    addOption(sweetnessOptions, product.fullSugar, "全糖", "sweetness");
    addOption(sweetnessOptions, product.lessSugar, "少糖", "sweetness");
    addOption(sweetnessOptions, product.halfSugar, "半糖", "sweetness");
    addOption(sweetnessOptions, product.quarterSugar, "微糖", "sweetness");
    addOption(sweetnessOptions, product.noSugar, "無糖", "sweetness");

    // 加料選項映射
    addOption(toppingsOptions, product.pearl, "珍珠", "topping");
    addOption(toppingsOptions, product.pudding, "布丁", "topping");
    addOption(toppingsOptions, product.coconutJelly, "椰果", "topping");
    addOption(toppingsOptions, product.taro, "芋園", "topping");
    addOption(toppingsOptions, product.herbalJelly, "仙草", "topping");

}

async function showProductModal(productId) {
    try {
        // 獲取產品詳細信息
        const response = await fetch(`/products/${productId}`);

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const product = await response.json();

        let buyNum = 1; // 默認購買數量為1
        buyNumInput.value = buyNum;

        // 設置單價和總價
        const unitPrice = Number.parseInt(product.productPrice); // 獲取產品單價
        totalAmt.textContent = unitPrice; // 顯示單價

        // 設置標題、價格和 size
        document.getElementById('productTitle').textContent = product.productName;
        document.querySelector('div.size').textContent = product.size;

        setModalDetail(product);
        attachOptionEventHandlers();
        updateAmt(buyNum, unitPrice);
        // 顯示模態框
        document.getElementById("lightbox").style.display = "block";
    } catch (error) {
        console.error('Error fetching product data:', error);
    }
}

document.getElementById('addToCartButton').addEventListener('click', function (e) {
    fetch('/totalusers', {
        method: 'GET', // 請求方法是 GET
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // 將返回的結果解析為 JSON 格式
        })
        .then(data => {
            if (!data.successful) {
                Swal.fire({
                    title: "請先登入",
                    text: "您必須登入才能將商品加入購物車！",
                    icon: "warning",
                    confirmButtonText: "確定"
                });
                // 隱藏 Lightbox 彈窗
                document.getElementById("lightbox").style.display = "none";
            } else {
                const success = addToCart(targetProductId, buyNumInput.value, storeIdNum); // 傳入購買數量

                // 如果成功，則移除事件監聽器並隱藏光箱
                if (success) {
                    // clearSelectedOptions();
                    document.getElementById("lightbox").style.display = "none"; // 隱藏光箱
                }
            }

        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
});


// 綁定點擊事件，點擊 li 後顯示模態框
document.addEventListener('click', (e) => {
    let targetClassName = e.target.classList.value;
    let checkedTarget = false;
    if (targetClassName === "menu") {
        targetProductId = e.target.closest("li").getAttribute("data-product-id");
        checkedTarget = true;
    } else if (targetClassName === "menuLi") {
        targetProductId = e.target.getAttribute("data-product-id");
        checkedTarget = true;
    }
    if (checkedTarget) {
        fetchProductImage(targetProductId);
        showProductModal(targetProductId);
    }
});

function updateAmt(buyNum, unitPrice) {
    document.addEventListener('click', (e) => {
        let targetClassName = e.target.classList.value;
        if (targetClassName === "plus") {
            buyNum = Math.min(buyNum + 1, 999); // 最大值999
        } else if (targetClassName === "minus") {
            buyNum = Math.max(buyNum - 1, 1); // 最小值1
        }
        buyNumInput.value = buyNum;
        totalAmt.textContent = parseInt(unitPrice) * parseInt(buyNum);
    });

}

// 顯示模態框並顯示產品信息


// 添加選項的輔助函數


function addToCart(productId, buyNum, storeIdNum) {
    console.log('店家信息:', storeIdNum);

    console.log("addToCart");
    console.log('localStorage before addToCart:', localStorage);

    if (localStorage.getItem("cart") == null) {

    } else {

    }


    // 读取并赋值
    let storedSize = localStorage.getItem('selectedSize');
    let storedTemperature = localStorage.getItem('selectedTemperature');
    let storedSweetness = localStorage.getItem('selectedSweetness');
    let storedToppings = localStorage.getItem('selectedToppings');

    console.log('Size:', storedSize);
    console.log('Temperature:', storedTemperature);
    console.log('Sweetness:', storedSweetness);
    console.log('Toppings:', storedToppings);

    // 确保选择的尺寸、温度和甜度有值
    let selectedSize = (storedSize && storedSize.trim()) || null;
    let selectedTemperature = (storedTemperature && storedTemperature.trim()) || null;
    let selectedSweetness = (storedSweetness && storedSweetness.trim()) || null;
    let selectedToppings = (storedToppings && storedToppings.trim()) || null;

    // 确认是否选择了所有必需选项
    if (!selectedSize) {
        Swal.fire({
            text: "尺寸未选！",
            icon: "warning"
        });
        return false; // 返回 false 以表示失败
    }

    if (!selectedTemperature) {
        Swal.fire({
            text: "温度未选！",
            icon: "warning"
        });
        return false; // 返回 false 以表示失败
    }

    if (!selectedSweetness) {
        Swal.fire({
            text: "甜度未选！",
            icon: "warning"
        });
        return false; // 返回 false 以表示失败
    }

    // 获取购物车数据并更新
    let cart = JSON.parse(localStorage.getItem('cart')) || {};
    cart[productId] = {
        storeId: storeIdNum,
        size: selectedSize,
        temperature: selectedTemperature,
        sweetness: selectedSweetness,
        toppings: selectedToppings || "未选择加料", // 如果未选择加料，使用默认值
        buyNum: buyNum
    };

    // 更新购物车到 localStorage
    localStorage.setItem('cart', JSON.stringify(cart));

    // 显示成功提示
    Swal.fire({
        text: "商品加入購物車成功!",
        icon: "success"
    });
    return true; // 添加成功，返回 true
}


// 為選項添加事件處理器
attachOptionEventHandlers();

// 設置選項事件處理的函數，與之前的一致
function attachOptionEventHandlers() {
    const sizeOption = document.querySelector('#sizeOptions .size');

    sizeOption.addEventListener('click', function () {
        sizeOption.style.backgroundColor = '#fac792';
        localStorage.setItem('selectedSize', sizeOption.textContent);
        // 改變背景顏色
    });


    document.querySelectorAll('#temperatureOptions .temperature').forEach(option => {
        option.addEventListener('click', () => {
            updateTemperatureColors(option);
            localStorage.setItem('selectedTemperature', option.textContent);
        });
    });
    document.querySelectorAll('#sweetnessOptions .sweetness').forEach(option => {
        option.addEventListener('click', () => {
            updateSweetnessColors(option);
            localStorage.setItem('selectedSweetness', option.textContent);
        });
    });
    document.querySelectorAll('#toppingsOptions .topping').forEach(option => {
        option.addEventListener('click', () => {
            updateToppingsColors(option);
            localStorage.setItem('selectedToppings', option.textContent);
        });
    });
}


// 改變選項顏色的函數 (與之前相同)
function updateTemperatureColors(selectedOption) {
    document.querySelectorAll('#temperatureOptions .temperature').forEach(option => {
        if (option === selectedOption) {
            option.style.backgroundColor = '#fac792';
        } else {
            option.style.backgroundColor = '';
        }
    });
}

function updateSweetnessColors(selectedOption) {
    console.log(selectedOption);

    document.querySelectorAll('#sweetnessOptions .sweetness').forEach(option => {
        if (option === selectedOption) {
            option.style.backgroundColor = '#fac792';
        } else {
            option.style.backgroundColor = '';
        }
    });
}


function updateToppingsColors(selectedOption) {
    document.querySelectorAll('#toppingsOptions .topping').forEach(option => {
        if (option === selectedOption) {
            option.style.backgroundColor = '#fac792';
        } else {
            option.style.backgroundColor = '';
        }
    });
}




