document.addEventListener("DOMContentLoaded",function(){
  //****************************************************控制購物流程階段變換及頁面跳轉****************************************
  //元素綁定
  //購物流程進度條
  let step1_el = document.getElementById("step1");
  let step2_el = document.getElementById("step2");
  let step3_el = document.getElementById("step3");
  //結帳流程-1
  //商家及使用者
  let store_info_el = document.getElementsByClassName("store-info")[0];
  let store_img_el = document.getElementsByClassName("store-img")[0];
  let store_name_el = document.getElementsByClassName("store-name")[0];
  let customer_img_el = document.getElementsByClassName("customer-img")[0];
  let customer_name_el = document.getElementsByClassName("customer-name")[0];

  //訂單細項
  let item_detail_el = document.getElementsByClassName('item-detail')[0];
  let ice_el = document.querySelector('.ice + .lightbox-radio-group');
  let sugar_el = document.querySelector('.sugar + .lightbox-radio-group');
  let materials_el = document.querySelector('.materials + .lightbox-radio-group');

  //訂單客製化選項標簽
  let recevier_method_el = document.getElementsByClassName('recevier-method')[0];
  //流程用標籤
  let order_detail_container_el = document.getElementsByClassName("order-detail-container")[0];
  let btn_addtopurchase_el =document.getElementsByClassName("btn-addtopurchase")[0];
  let btn_checkout_el = document.getElementsByClassName("btn-checkout")[0];
  let step_content_el = document.getElementsByClassName("step-content")[0];
  let step_content2_el = document.getElementsByClassName("step-content2")[0];
  let step_content3_el = document.getElementsByClassName("step-content3")[0];

  //燈箱
  let lightbox_el = document.getElementById("lightbox");
  let qtyminus_el = document.getElementsByClassName("qtyminus")[0];
  let qtyplus_el = document.getElementsByClassName("qtyplus")[0];
  let qty_el = document.getElementsByClassName("qty")[0];
  let lightbox_delete_el = document.getElementById("lightbox-delete");
  let delete_detail_el = document.getElementsByClassName("delete-detail")[0];
  let lightbox_content_el = document.getElementsByClassName("lightbox-content")[0];
  let confirm_delete_el = document.getElementById("confirm-delete");
  let cancel_delete_el = document.getElementById("cancel-delete");


  //取貨方式
  let pick_up_el = document.getElementsByClassName('pick-up')[0];
  let pick_up_input_el = document.getElementsByClassName("pick-up-input")[0];

  //時間選擇
  let picker_el = document.getElementsByClassName("pickuper")[0];

  //優惠使用
  let coupon_number_el = document.getElementsByClassName("coupon-number")[0];
  let select_coupon_input_el = document.getElementsByClassName("select-coupon-input")[0];
  let select_membercard_input_el = document.getElementsByClassName("select-membercard-input")[0];
  let select_moneybag_input_el = document.getElementsByClassName("select-moneybag-input")[0];
  let moneybag_discount_number_el = document.getElementsByClassName("moneybag-discount-number")[0];
  //結帳流程-2
  //取貨人
  let btn_backto_last_page_el = document.getElementsByClassName("btn-backto-last-page")[0];
  let btn_goto_next_page_el = document.getElementsByClassName("btn-goto-next-page")[0];
  let select_cellphone_el = document.getElementsByClassName("select-cellphone")[0];
  let input_cellphone_el = document.getElementsByClassName("input-cellphone")[0];
  let select_phone_el = document.getElementsByClassName("select-phone")[0];
  let input_phone_zone_el = document.getElementsByClassName("input-phone-zone")[0];
  let input_phone_number_el = document.getElementsByClassName("input-phone-number")[0];
  let text2store_el = document.getElementsByClassName("text2store")[0];
  //發票方式
  let select_paper_el = document.getElementsByClassName("select-paper")[0]; 
  let select_vehicle_el = document.getElementsByClassName("select-vehicle")[0]; 
  let checksava_vehicle_el = document.getElementsByClassName("checksava-vehicle")[0];
  let vehicle_number_el = document.getElementsByClassName("vehicle-number")[0];
  let select_paper_uniform_el = document.getElementsByClassName("select-paper-uniform")[0];
  let uniform_numbers_el = document.getElementsByClassName("uniform-numbers")[0];
  //結帳流程-3
  let btn_backto_last_page2_el = document.getElementsByClassName("btn-backto-last-page2")[0];
  let btn_submit_order_el = document.getElementsByClassName("btn-submit-order")[0];
  let btn_modal_close_el = document.getElementsByClassName("btn_modal_close")[0];

  //一進到結帳選擇頁面先用localstorage抓DB以及獲取目前登入用戶ID
  //TODO 獲得用戶ID以抓取資料渲染
  // const customerid = localStorage.getItem('customerId');
  // fetch(`/cart/checkoutlist/{customerID}`)

  //獲得localstorage資料(購物車)
  const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
  //localstorage購物車資料分類
  const groupedItems = sortCartItems(cartItems);
  // 提取所有商品的 productId用來渲染網頁，一個ID只抓一次
  const productIds = [...new Set(groupedItems.map(item => item.productId))];
  //獲得商品ID列獲得的商品，並在方法中渲染前端頁面
  findproductbyid(productIds);
  // 把localstorage資料存到後端資料庫，並且抓取資料渲染
  // saveallproduct();

  //********************************************第一頁按鈕事件*************************************
  //燈箱數量增減
    qtyminus_el.addEventListener("click", function (e) {
        if (qty_el.value > 0) {
            qty_el.value--;
        }
        e.stopPropagation();
    })
    qtyplus_el.addEventListener("click", function (e) {
        if (qty_el.value >= 0) {
            qty_el.value++;
        }
        e.stopPropagation();
    })
  //刪除訂餐
      var currentItem = null;
      // for (let i = 0; i < btn_delete_el.length; i++) {
      //   btn_delete_el[i].addEventListener("click", function (e) {
      //     lightbox_delete_el.style.display = "flex";
      //     document.body.style.overflow = 'hidden';
      //     document.body.style.paddingRight = '17px';
      //
      //     currentItem = this.closest(".item-content");
      //     var productName = this.closest(".cart-item").querySelector(".product-name").textContent;
      //     delete_detail_el.innerHTML = `刪除 <span class="highlight">${productName}</span> ?`;
      //   })
      // }

      lightbox_delete_el.addEventListener("click", function () {
        lightbox_delete_el.style.display = "none";
        document.body.style.overflow = 'auto';
        document.body.style.paddingRight = '0px';
      });
      lightbox_content_el.addEventListener("click", function (e) {
        e.stopPropagation();
      });

      confirm_delete_el.addEventListener("click", function () {
        currentItem.remove();
        lightbox_delete_el.style.display = "none";
        document.body.style.overflow = 'auto';
        document.body.style.paddingRight = '0px';
        currentItem = null;
      })
      cancel_delete_el.addEventListener("click", function () {
        lightbox_delete_el.style.display = "none";
        document.body.style.overflow = 'auto';
        document.body.style.paddingRight = '0px';
      });
      //時間選擇
      picker_el.addEventListener("focus", function () {
        picker_el.classList.add("focus-border");
      })
      picker_el.addEventListener("blur", function () {
        picker_el.classList.remove("focus-border");
      });

      //優惠使用(優惠券、會員卡、會員錢包)
      select_coupon_input_el.addEventListener("click", function () {
        coupon_number_el.disabled = false;
        coupon_number_el.focus();
      })
      coupon_number_el.addEventListener("focus", function () {
        coupon_number_el.placeholder = "";
      });
      coupon_number_el.addEventListener("blur", function () {
        if (coupon_number_el.value === "") {
          coupon_number_el.placeholder = "請輸入優惠券序號";
        }
      });

      //TODO 加入點擊事件get會員卡點數 點選會員卡，
      select_membercard_input_el.addEventListener("click",function (){

      })
      //TODO 加入點擊事件get會員錢包餘額
      select_moneybag_input_el.addEventListener("click",function (){
        moneybag_discount_number_el.disabled =false;
        moneybag_discount_number_el.focus();
      })
      moneybag_discount_number_el.addEventListener("focus", function () {
        moneybag_discount_number_el.placeholder = "";
      });
      moneybag_discount_number_el.addEventListener("blur", function () {
        if (moneybag_discount_number_el.value === "") {
          moneybag_discount_number_el.placeholder = "請輸入折抵金額";
        }
      });

      //頁面跳轉
      btn_addtopurchase_el.addEventListener("click", function (e) {
        console.log("預計跳回該商家頁面");
        e.stopPropagation();
      })

      btn_checkout_el.addEventListener("click", function (e) {
        step1_el.classList.remove("active");
        step2_el.classList.add("active");
        if (step_content_el.style.display = "flex") {
          step_content_el.style.display = "none";
          step_content2_el.style.display = "flex";
          order_detail_container_el.scrollIntoView({
            behavior: "smooth" // 使用平滑滾動效果
          });
        }
        e.stopPropagation();
      })


//********************************************日期時間選擇器*************************************
      new AirDatepicker('#myDatepicker', {
        locale: {
          days: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
          daysShort: ['日', '一', '二', '三', '四', '五', '六'],
          daysMin: ['日', '一', '二', '三', '四', '五', '六'],
          months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
          monthsShort: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
          today: '今天',
          clear: '清除',
          dateFormat: 'yyyy-MM-dd',
          timeFormat: 'HH:mm',
          firstDay: 1
        },
        timepicker: true,
      });

      //********************************************第二頁按鈕事件*************************************
      //取貨人資料
      select_cellphone_el.addEventListener("click", function () {
        input_cellphone_el.disabled = false;
        input_phone_zone_el.disabled = true;
        input_phone_number_el.disabled = true;
        input_phone_zone_el.value = "";
        input_phone_number_el.value = "";
        input_cellphone_el.focus();
      })
      input_cellphone_el.addEventListener("focus", function () {
        input_cellphone_el.placeholder = "";
      });
      input_cellphone_el.addEventListener("blur", function () {
        if (input_cellphone_el.value === "") {
          input_cellphone_el.placeholder = "EX:0912345678";
        }
      });

      select_phone_el.addEventListener("click", function () {
        input_phone_zone_el.disabled = false;
        input_cellphone_el.disabled = true;
        input_cellphone_el.value = "";
        input_phone_zone_el.focus();
      })
      input_phone_zone_el.addEventListener("focus", function () {
        input_phone_zone_el.placeholder = "";
      });
      input_phone_zone_el.addEventListener("blur", function () {
        if (input_phone_zone_el.value === "") {
          input_phone_zone_el.placeholder = "區碼";
        }
      });
      input_phone_zone_el.addEventListener("input", function () {
        if (input_phone_zone_el.value.length === 2) {
          setTimeout(function () {
            input_phone_number_el.disabled = false;
            input_phone_number_el.focus();
          }, 10);
        }
      })

      text2store_el.addEventListener("focus", function () {
        text2store_el.placeholder = "";
      });
      text2store_el.addEventListener("blur", function () {
        if (text2store_el.value === "") {
          text2store_el.placeholder = "請輸入文字...";
        }
      });

      //發票方式
      select_paper_el.addEventListener("click", function () {
        vehicle_number_el.disabled = true;
        uniform_numbers_el.disabled= true;
        checksava_vehicle_el.disabled = true;
        checksava_vehicle_el.checked = false;
        uniform_numbers_el.value="";
      })
      select_vehicle_el.addEventListener("click", function () {
        checksava_vehicle_el.disabled = false;
        vehicle_number_el.disabled = false;
        uniform_numbers_el.disabled= true;
        vehicle_number_el.focus();
        uniform_numbers_el.value="";
      })
      select_paper_uniform_el.addEventListener("click",function (){
        uniform_numbers_el.disabled=false;
        vehicle_number_el.disabled = true;
        checksava_vehicle_el.disabled = true;
        checksava_vehicle_el.checked = false;
        uniform_numbers_el.focus();
        vehicle_number_el.value="";
      })
      uniform_numbers_el.addEventListener("focus", function () {
        uniform_numbers_el.placeholder = "";
      });
      uniform_numbers_el.addEventListener("blur", function () {
        if (uniform_numbers_el.value === "") {
          uniform_numbers_el.placeholder = "請輸入統編";
        }
      });
      //頁面跳轉
      btn_backto_last_page_el.addEventListener("click", function (e) {
        step2_el.classList.remove("active");
        step1_el.classList.add("active");
        if (step_content2_el.style.display = "flex") {
          step_content2_el.style.display = "none";
          step_content_el.style.display = "flex";
          order_detail_container_el.scrollIntoView({
            behavior: "smooth" // 使用平滑滾動效果
          });
        }
        e.stopPropagation()
      })

      btn_goto_next_page_el.addEventListener("click", function (e) {
        step2_el.classList.remove("active");
        step3_el.classList.add("active");
        if (step_content2_el.style.display = "flex") {
          step_content2_el.style.display = "none";
          step_content3_el.style.display = "flex";
          order_detail_container_el.scrollIntoView({
            behavior: "smooth" // 使用平滑滾動效果
          });
        }
        e.stopPropagation()
      })

      //********************************************第三頁按鈕事件*************************************
      btn_backto_last_page2_el.addEventListener("click", function (e) {
        step3_el.classList.remove("active");
        step2_el.classList.add("active");
        if (step_content3_el.style.display = "flex") {
          step_content3_el.style.display = "none";
          step_content2_el.style.display = "flex";
          order_detail_container_el.scrollIntoView({
            behavior: "smooth" // 使用平滑滾動效果
          });
        }
        e.stopPropagation()
      })
    //********************************************function區****************************************
    //GET 請求：進入購物結帳頁面取得使用者

    //POST 請求：儲存購物車資料
    // function saveallproduct(){
    //   if (cartItems.length === 0) {
    //     alert('Your cart is empty');
    //     // window.location.href = '/home'; // 跳转到你想要的页面
    //     return;
    //   }
    //   fetch('/cart/checkoutlist/saveItems', {
    //     method: 'POST',
    //     headers: {
    //       'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify(cartItems)
    //   })
    //       .then(response => response.text())
    //       .then(data => {
    //         console.log('Success:', data);
    //       })
    //       .catch((error) => {
    //         console.error('Error:', error);
    //       });
    // }
  //localstorage購物車資料分類
  function sortCartItems(cartItems){
    const groupedItems = [];
    //將全部購物車項目依序讀出分類、累加
    cartItems.forEach(item => {
      //如果找到符合條件項目則返回該項目，沒有找到則返回undefined
      const existingItem = groupedItems.find(groupedItem =>
          groupedItem.productId === item.productId &&
          groupedItem.sugar === getSugarOption(item) &&
          groupedItem.ice === getIceOption(item) &&
          groupedItem.add_ons === getAddOns(item) &&
          groupedItem.size === item.size
      );
      //找到商品ID一樣甜度冰塊加料尺寸都一樣的就累加數量
      if (existingItem) {
        existingItem.quantity += item.quantity;
      } else {
        //沒有找到就存入新的項目，只存需要的欄位
        groupedItems.push({
          productId: item.productId,
          sugar: getSugarOption(item),
          ice: getIceOption(item),
          add_ons: getAddOns(item),
          size: item.size,
          quantity: item.quantity
        });
      }
    });
    return groupedItems;
  }
    //用商品ID抓商品資料及店家資訊
    function findproductbyid(productIds){
      fetch('/cart/checkoutlist/findByproductIds', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(productIds)
      })
          .then(response => {
            if (!response.ok) {
              throw new Error('回應異常 回應代碼: ' + response.status);
            }
            return response.json();
          })
          .then(products => {
            // 检查是否有返回商品
            if (!products || products.length === 0) {
              throw new Error('無商品或對應的商品');
            }
            console.log('Products:', products);
            // 检查商品資料中是否包含商店ID
            const storeId = products[0].productStoreId;
            if (!storeId) {
              throw new Error('商品缺乏商店編號');
            }
            console.log('storeId:',storeId);
            //獲取商店資訊
            findstorebyid(storeId);
            renderproductdetail(groupedItems,products);
          })
          .catch(error => {
            console.error('獲取商品時出錯:', error);
            alert('進入購物結帳失敗');
          });
    }

    //用商品得到的商店ID來抓店家資訊
    function findstorebyid(storeId){
        fetch('/cart/checkoutlist/findByStoreId',{
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(storeId)
        })
            .then(response => {
              if (!response.ok) {
                throw new Error('回傳商店資料異常');
              }
              return response.json();
            })
            .then(store => {
              if (!store) {
                throw new Error('找不到商店資料');
              }
              console.log('Store Details:', store);
              store_name_el.innerHTML = store.storeName || '無此店家';
              updateDeliveryOptions(store.isDelivery);
            })
            .catch(error => {
              console.error('Error:', error);
            });
    }
    //判斷店家是否有外送選項
    function updateDeliveryOptions(isDelivery){
      if (isDelivery){
        const carryoutdiv = document.createElement('div');
        carryoutdiv.className = 'carry-out';
        carryoutdiv.innerHTML=`
          <div class="carry-out-selection">
              <input class="carry-out-radio" type="radio" name="delivery" id="carry-out">
              <label class="carry-out-label" for="carry-out" style="padding-top: 10px">外送</label>
          </div>
           <div class="carry-out-address">
               <input type="text" class="address" placeholder="請輸入地址" maxlength="255" disabled>
           </div>
            `;
        pick_up_el.insertAdjacentElement('afterend', carryoutdiv);
        //設定標籤&綁定事件
        let carry_out_radio_el = document.getElementsByClassName("carry-out-radio")[0];
        let address_el = document.getElementsByClassName("address")[0];
        carry_out_radio_el.addEventListener("click", function () {
          address_el.disabled = false;
          address_el.focus();
        })
        address_el.addEventListener("focus", function () {
          // 清空 placeholder
          address_el.placeholder = "";
        });
        // 當輸入框失去焦點時
        address_el.addEventListener("blur", function () {
          // 如果輸入框為空，恢復 placeholder
          if (address_el.value === "") {
            address_el.placeholder = "請輸入地址";
          }
        });
        //點擊自取選項關閉地址輸入框
        pick_up_input_el.addEventListener("click", function () {
          address_el.disabled = true;
        })
      }
    }
  //用Localstotage中的購物車商品和返回的商品資訊生成商品明細
  function renderproductdetail(groupedItems, products){
    // 建立商品ID到商品資訊的映射
    console.log('分類後購物車商品列表: ',groupedItems)
    const productMap = new Map();
    products.forEach(product => {
      productMap.set(product.productId, {
        productName: product.productName,
        productPrice: product.productPrice
      });
    });
    //遍歷整理後的購物車商品列，動態生成標籤
    groupedItems.forEach(item =>{
      const product = productMap.get(item.productId);
      if(product){
        const itemContent = document.createElement('div');
        const cartItem = document.createElement('div');
        itemContent.className = 'item-content';
        cartItem.className = 'cart-item';
        cartItem.innerHTML = `
            <p class="product-name">${product.productName}</p>
            <div class="product-buttons">
              <button class="edit-btn"><i class="fas fa-edit"></i></button>
              <button class="delete-btn"><i class="fas fa-trash"></i></button>
            </div>
            `;
            itemContent.appendChild(cartItem);
        //商品細項:根據整理的購物車商品資料填入明細
        const productDetail = document.createElement('div');
        productDetail.className = 'product-detail';
        productDetail.innerHTML = `
          <div class="detail-item" data-type="sugar">${item.sugar} /</div>
          <div class="detail-item" data-type="ice">${item.ice} /</div>
          <div class="detail-item" data-type="add-ons">${item.add_ons} /</div>
          <div class="detail-item" data-type="price">$${product.productPrice}/ </div>
          <div class="detail-item" data-type="size">${item.size} /</div>
          <div class="detail-item" data-type="quantity">${item.quantity} 杯</div>
            `;
        //將明細放到商品資訊欄
        itemContent.appendChild(productDetail);
        //把彙整後商品資訊放到購物明細區塊
        item_detail_el.appendChild(itemContent);
        // 動態綁定 edit-btn 事件
        //找到最接近點擊的商品項的編輯按鈕
        const editBtn = cartItem.querySelector('.edit-btn');
        editBtn.addEventListener('click',function (){
          //修改: 開啟燈箱
          lightbox_el.style.display = "flex";
          document.body.style.overflow = 'hidden';
          document.body.style.paddingRight = '17px';
          // 找到點擊最近的商品項目，將對應的商品名稱顯示在燈箱
          let button = this;
          let cartItem = button.closest(".cart-item");
          let productName = cartItem.querySelector(".product-name").textContent;
          let lightboxTitle = document.querySelector("#lightbox h1");
          lightboxTitle.textContent = productName;
          //獲得商品選項(冰塊甜度加料選項 目前先用固定)

          //儲存最後燈箱選擇的項目
          let selectedIce = ice_el.querySelector('input[name="ice-options"]:checked');
          let selectedSugar = sugar_el.querySelector('input[name="sugar-options"]:checked');
          let selectedMaterials = materials_el.querySelector('input[name="materials-options"]:checked');
          //燈箱更新商品按鈕事件綁定
          btn_modal_close_el.addEventListener("click", function () {
            //指定到該點擊更新按鈕下最近的商品細項父節點
            const itemContent = cartItem.closest(".item-content");
            //再利用父節點指定到其下的細項
            const productDetail = itemContent.querySelector(".product-detail");

            lightbox_el.style.display = "none";
            document.body.style.overflow = 'auto';
            document.body.style.paddingRight = '0px';
          })
        })
      }
    })
  }

  //判斷冰塊
      function getIceOption(Item) {
        if (Item.normal_ice) return '正常冰';
        if (Item.less_ice) return '少冰';
        if (Item.ice_free) return '去冰';
        if (Item.light_ice) return '微冰';
        if (Item.room_temperature) return '常溫';
        if (Item.hot) return '熱飲';
        return '未選擇';
      }
    //判斷甜度
      function getSugarOption(Item) {
        if (Item.full_sugar) return '全糖';
        if (Item.less_sugar) return '少糖';
        if (Item.half_sugar) return '半糖';
        if (Item.quarter_sugar) return '微糖';
        if (Item.no_sugar) return '無糖';
        return '未選擇';
      }
    //判斷加料
      function getAddOns(Item) {
        if (Item.pearl) return '加珍珠';
        if (Item.pudding) return '加布丁';
        if (Item.coconut_jelly) return '加椰果';
        if (Item.taro) return '加芋頭';
        if (Item.herbal_jelly) return '加仙草';
        return '無加料';
      }

  // 獲取尺寸的輔助函數
  //   function getSize(size) {
  //     switch (size) {
  //       case 1:
  //         return '小杯';
  //       case 2:
  //         return '中杯';
  //       case 3:
  //         return '大杯';
  //       default:
  //         return '未知尺寸';
  //     }
  //   }
      // 提供商品選項
      //       function fetchProductOptions(productId){
      //         fetch(`/getProductOptions?productName=${encodeURIComponent(productName)}`)
      //         .then(response => response.json())
      //         .then(data =>{
      //           data.ice.forEach(option => {
      //            ice_el +=
      //            <input type="radio" id="${option.id}" name="ice-options" value="${option.value}">
      //            <label for="${option.id}">${option.label}</label>
      //           });
      //           data.sugar.forEach(option => {
      //                  sugar_el +=
      //                  <input type="radio" id="${option.id}" name="sugar-options" value="${option.value}">
      //                  <label for="${option.id}">${option.label}</label>
      //                 });
      //           data.materials.forEach(option => {
      //                  materials_el +=
      //                  <input type="radio" id="${option.id}" name="materials-options" value="${option.value}">
      //                  <label for="${option.id}">${option.label}</label>
      //                });
      //         });
      //       }
    })


