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
  // let ice_el = document.querySelector('.ice + .lightbox-radio-group');
  // let sugar_el = document.querySelector('.sugar + .lightbox-radio-group');
  // let materials_el = document.querySelector('.materials + .lightbox-radio-group');

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
  let btnCloseLightbox = document.querySelector('.btn_close_lightbox');
  let qtyminus_el = document.getElementsByClassName("qtyminus")[0];
  let qtyplus_el = document.getElementsByClassName("qtyplus")[0];
  let qty_el = document.getElementsByClassName("qty")[0];
  let lightbox_delete_el = document.getElementById("lightbox-delete");
  let delete_detail_el = document.getElementsByClassName("delete-detail")[0];
  let lightbox_content_el = document.getElementsByClassName("lightbox-content")[0];
  let confirm_delete_el = document.getElementById("confirm-delete");
  let cancel_delete_el = document.getElementById("cancel-delete");
  //用全域變數用來裝cartItem(用來編輯)、currentItem(用來刪除)、loginMember(用來裝目前登入會員)
    let currentCartItem = null;
    let currentItem = null;
    let loginMember = null;

  //取貨方式
  let pick_up_el = document.getElementsByClassName('pick-up')[0];
  let pick_up_input_el = document.getElementsByClassName("pick-up-input")[0];

  //時間選擇
  // let picker_el = document.getElementsByClassName("pickuper")[0];
    let date_input_el = document.getElementById('myDatepicker');

  //優惠使用
  // let coupon_number_el = document.getElementsByClassName("coupon-number")[0];
  let select_coupon_input_el = document.getElementsByClassName("select-coupon-input")[0];
  let select_membercard_input_el = document.getElementsByClassName("select-membercard-input")[0];
  let select_moneybag_input_el = document.getElementsByClassName("select-moneybag-input")[0];
  let moneybag_discount_number_el = document.getElementsByClassName("moneybag-discount-number")[0];
  let couponDropdown_el = document.getElementsByClassName('coupon-dropdown')[0];
  let couponSelect_el = document.getElementById('coupon-select');
  let coupon_minus_number_el = document.getElementsByClassName('coupon-minus-number')[0];
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

  //一進到結帳選擇頁面先用localstorage抓DB以及獲取sessionstorage目前登入用戶ID
  //TODO 獲得用戶ID以抓取資料渲染
  const customer = sessionStorage.getItem('customer');
  if(customer){
      let customerData = JSON.parse(customer);
      let customerId = customerData.customerId;
      fetch(`/cart/checkoutlist/${customerId}`)
          .then(response=>{
              if (!response.ok) {
                  return response.json().then(errorData => {
                      throw new Error(errorData.message);
                  });
              }
              return response.json();
          })
          .then(member=>{
              if(member ==null){
                  Swal.fire({
                      title: '查無此會員資料',
                      text: '查無此會員資料 ID='+customerid,
                      icon: 'error'
                  });
              }
              console.log('Member Details:', member);
              //將取得會員資訊儲存
              loginMember = member;
              //放入會員資料
              customer_name_el.textContent = member.nickname;
          })
          .catch(error => {
              Swal.fire({
                  title: '取得會員數據異常',
                  text: error.message,
                  icon: 'error'
              });
          })
    }


  //獲得localstorage資料(購物車)
  const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
  //localstorage購物車資料分類
  const groupedItems = sortCartItems(cartItems);
  console.log('分類後購物車商品列表: ',groupedItems)
  // 提取所有商品的 productId用來渲染網頁，一個ID只抓一次
  const productIds = [...new Set(groupedItems.map(item => item.productId))];
  //獲得商品ID列獲得的商品，並在方法中渲染前端頁面
  findproductbyid(productIds);
  // 把localstorage資料存到後端資料庫，並且抓取資料渲染
  // saveallproduct();

  //********************************************第一頁按鈕事件*************************************
  //燈箱:數量增減
    qtyminus_el.addEventListener("click", function (e) {
        if (qty_el.value > 1) {
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
  //燈箱:取消編輯
  btnCloseLightbox.addEventListener('click',function (){
    lightbox_el.style.display = "none";
    document.body.style.overflow = 'auto';
    document.body.style.paddingRight = '0px';
  })
  //燈箱:刪除品項
      confirm_delete_el.addEventListener("click", function () {
        currentItem.remove();
        lightbox_delete_el.style.display = "none";
        document.body.style.overflow = 'auto';
        document.body.style.paddingRight = '0px';
        currentItem = null;
        //編輯過商品同步修改localstorage
        updateLocalStorage();
      })
      cancel_delete_el.addEventListener("click", function () {
        lightbox_delete_el.style.display = "none";
        document.body.style.overflow = 'auto';
        document.body.style.paddingRight = '0px';
      });
      //時間選擇
      // picker_el.addEventListener("focus", function () {
      //   picker_el.classList.add("focus-border");
      // })
      // picker_el.addEventListener("blur", function () {
      //   picker_el.classList.remove("focus-border");
      // });

      //優惠使用(優惠券、會員卡、會員錢包)
      select_coupon_input_el.addEventListener("click", function () {
          couponSelect_el.disabled=false;
          getcoupons(loginMember.customerId);
      })

      //點選優惠券時動態更新結帳明細折抵金額
      couponSelect_el.addEventListener('change',function (){
          let selectedOption = couponSelect_el.options[couponSelect_el.selectedIndex];
          let discount = selectedOption.getAttribute('data-discount');
          coupon_minus_number_el.textContent = `$${discount}`;
      })

      //TODO 加入點擊事件get會員卡點數 點選會員卡，
      select_membercard_input_el.addEventListener("click",function (){
          //清除優惠券欄位
          couponSelect_el.innerHTML = '<option disabled selected >請選擇優惠券</option>';
          coupon_minus_number_el.textContent = '$0';
          couponSelect_el.disabled=true;
      })
      //TODO 加入點擊事件get會員錢包餘額

      select_moneybag_input_el.addEventListener("click",function (){
          couponSelect_el.disabled=true;
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
    // 取得當前日期和時間
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
        minDate: new Date(), // 設定不能選擇早於當前的日期
        timepicker: true, // 開啟時間選擇功能
        minutesStep: 1 // 設定分鐘選擇間隔
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
  //F01 localstorage購物車資料分類
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
    //F02 用商品ID抓商品資料及店家資訊 sortCartItems->findproductbyid
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
              Swal.fire({
                  title: '錯誤',
                  text: error,
                  icon: 'error'
              });
          });
    }

    //F03 用商品得到的商店ID來抓店家資訊
    //findproductbyid->findstorebyid
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
              //判斷現在是不是營業時間及是否接單
              //   if (!isinOpeningHours(store.openingHours, store.closingHours)
              //       || !store.isTakeOrders) {
              //       Swal.fire({
              //           title: '錯誤',
              //           text: `目前該店 ${store.storeName} 不在營業時間或不接單！`,
              //           icon: 'error',
              //           confirmButtonText: '確定'
              //       }).then(() => {
              //           window.location.href = 'homePage.html';
              //           // 之後改成跳回上一頁
              //           // window.history.back();
              //       });
              //   }
                //渲染店名
              store_name_el.innerHTML = store.storeName || '無此店家';
                //處理商家logo
              if(store.logo){
                  //將圖片轉base64格式
                  // const base64Logo = arrayBufferToBase64(store.logo);
                  const imageFormat = detectImageFormat(store.logo);
                  store_img_el.src = `data:image/${imageFormat};base64,${store.logo}`;
              }
              //是否提供外送選項
              updateDeliveryOptions(store.isDelivery);
            })
            .catch(error => {
              console.error('Error:', error);
            });
    }
    //F04 判斷圖片檔案格式
    function detectImageFormat(base64String) {
        if (base64String.startsWith("data:image/jpeg")) {
            return 'jpeg';
        } else if (base64String.startsWith("data:image/png")) {
            return 'png';
        }
        // 如果格式無法識別，返回png
        return 'png';
    }
    //轉換店家圖片格式
    //findproductbyid->findstorebyid>arrayBufferToBase64
    // function arrayBufferToBase64(buffer) {
    //     let binary = '';
    //     let bytes = new Uint8Array(buffer);
    //     let len = bytes.byteLength;
    //     for (let i = 0; i < len; i++) {
    //         binary += String.fromCharCode(bytes[i]);
    //     }
    //     return window.btoa(binary);
    // }
    //F05 判斷是否目前時間為營業時間
    function isinOpeningHours(openingHours, closingHours) {
        const now = new Date();
        //["HH", "MM", "SS"]->[HH, MM, SS]->[openHours(HH), openMinutes(MM)]
        const [openHours, openMinutes] = openingHours.split(':').map(Number);
        const [closeHours, closeMinutes] = closingHours.split(':').map(Number);

        // Construct Date objects for opening and closing times
        const openTime = new Date(now);
        openTime.setHours(openHours, openMinutes, 0, 0);

        const closeTime = new Date(now);
        closeTime.setHours(closeHours, closeMinutes, 0, 0);

        // 商店的營業時間跨越午夜情形
        if (closeTime < openTime) {
            closeTime.setDate(closeTime.getDate() + 1);
        }
        //判斷現在時間是否落在營業區間返回true,false
        return now >= openTime && now <= closeTime;
    }

    //F06 判斷店家是否有外送選項
    //findproductbyid->findstorebyid->updateDeliveryOptions
    function updateDeliveryOptions(isDelivery){
      if (isDelivery){
        //獲得用戶常用地址

        //外送(常用地址)
        // const carryoutdefalut = document.createElement('div');
        // carryoutdefalut.className ='carry-out-default';
        // carryoutdefalut.innerHTML=`
        //     <input class="carry-out-default-radio" type="radio" name="delivery" id="carry-out-default">
        //     <label class="carry-out-default-label" for="carry-out-default" style="padding-top: 10px">外送(常用地址)</label>
        //     <div class="common-address-options" style="display: none;">
        //     </div>
        //
        //     `;
        //外送(自填地址)
        const carryoutdiv = document.createElement('div');
        carryoutdiv.className = 'carry-out';
        carryoutdiv.innerHTML=`
          <div class="carry-out-selection">
              <input class="carry-out-radio" type="radio" name="delivery" id="carry-out">
              <label class="carry-out-label" for="carry-out" style="padding-top: 10px">外送(自填地址)</label>
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
          address_el.value='';
        })
      }
    }
    //用customerID獲得常用地址
    // function getAddressbyId(customer){
    //
    // }
    //F07 用Localstotage中的購物車商品和返回的商品資訊生成商品明細
    //sortCartItems->findproductbyid->renderproductdetail
    function renderproductdetail(groupedItems, products){
    // 建立商品ID到商品資訊的映射
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
            //將productId放入data-attribute中，後續更新Localstorage可取用
            cartItem.dataset.productId = item.productId;
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
            //鎖定這一次新建立的cartItem底下的編輯按鈕
            const editBtn = cartItem.querySelector('.edit-btn');
            editBtn.addEventListener('click',function (){
              //修改: 開啟燈箱，並重置燈箱選項
              document.querySelectorAll(`input[name="ice-options"]`)
                  .forEach(radio=>{radio.checked = false});
              document.querySelectorAll(`input[name="sugar-options"]`)
                  .forEach(radio=>{radio.checked = false});
              document.querySelectorAll(`input[name="materials-options"]`)
                  .forEach(radio=>{radio.checked = false});
                document.querySelectorAll(`input[name="size-options"]`)
                    .forEach(radio=>{radio.checked = false});
              qty_el.value = 1;

              lightbox_el.style.display = "flex";
              document.body.style.overflow = 'hidden';
              document.body.style.paddingRight = '17px';
              // 找到此項點擊編輯的商品項，將對應的商品名稱顯示在燈箱
              let button = this;  //指這次的點擊目標
              let cartItem = button.closest(".cart-item");
              let productName = cartItem.querySelector(".product-name").textContent;
              let lightboxTitle = document.querySelector("#lightbox h1");
              lightboxTitle.textContent = productName;
              // 保存當前的 cartItem 到全局變數
                currentCartItem = cartItem;
              //獲得商品選項(冰塊甜度加料選項 目前先用固定)

              //處理燈箱編輯事件
                btn_modal_close_el.removeEventListener('click',handlelightbox);
                btn_modal_close_el.addEventListener('click',handlelightbox);
            })    //editBtn End
              //刪除頁面商品細項
              const deleteBtn = cartItem.querySelector('.delete-btn');

              deleteBtn.addEventListener('click',function (){
                  lightbox_delete_el.style.display = "flex";
                  document.body.style.overflow = 'hidden';
                  document.body.style.paddingRight = '17px';

                  currentItem = this.closest(".item-content");
                  let productName = this.closest(".cart-item").querySelector(".product-name").textContent;
                  delete_detail_el.innerHTML = `
                    刪除 <span class="highlight">${productName}</span> ?
                    `;
              })
           }
        })
      }


    //F08 燈箱:編輯事件處理
    function handlelightbox(){
          //先檢查全域變數是否存在cartItem
        if (!currentCartItem) return;
            const selectedIce = document.querySelector('input[name="ice-options"]:checked');
            const selectedSugar = document.querySelector('input[name="sugar-options"]:checked');
            const selectedMaterials = document.querySelector('input[name="materials-options"]:checked');
            const selectedSize = document.querySelector('input[name="size-options"]:checked');
            const newQuantity = parseInt(qty_el.value);
            //確認冰塊、甜度、加料、尺寸皆有選
            if (!selectedIce || !selectedSugar || !selectedMaterials|| !selectedSize) {
                Swal.fire({
                    icon: 'warning',
                    title: '請選擇所有項目',
                    text: '請選擇所有選項（冰塊、甜度、加料、尺寸）。',
                    confirmButtonText: '確定'
                });
                return;
            }
            //設定選中的選項文字
            const iceText = selectedIce.nextElementSibling.textContent;
            const sugarText = selectedSugar.nextElementSibling.textContent;
            const materialsText = selectedMaterials.nextElementSibling.textContent;
            const sizeText = selectedSize.nextElementSibling.textContent;
            //指定到該點擊更新按鈕下最近的商品細項父節點
            const itemContent = currentCartItem.closest(".item-content");
            //再利用父節點指定到其下的細項
            const productDetail = itemContent.querySelector(".product-detail");

            //先把這次更動細項存下來後續比對用
            const currentProduct = {
                productName: currentCartItem.querySelector(".product-name").textContent,
                ice: iceText,
                sugar: sugarText,
                materials: materialsText,
                size: sizeText
            };
            //更新頁面商品細項
            productDetail.querySelector('[data-type="ice"]').textContent = `${iceText} / `;
            productDetail.querySelector('[data-type="sugar"]').textContent = `${sugarText} / `;
            productDetail.querySelector('[data-type="add-ons"]').textContent = `${materialsText} / `;
            productDetail.querySelector('[data-type="size"]').textContent = `${sizeText} / `;
            productDetail.querySelector('[data-type="quantity"]').textContent = `${newQuantity} 杯`;

            // 遍歷購物車中的商品，檢查是否有相同品名和選項的商品
            const cartItems = document.querySelectorAll(".cart-item");
            cartItems.forEach(item =>{
                const itemProductName = item.querySelector(".product-name").textContent;
                const itemIce = item.closest(".item-content").querySelector('[data-type="ice"]').textContent.trim().replace(/ \/$/, '');
                const itemSugar = item.closest(".item-content").querySelector('[data-type="sugar"]').textContent.trim().replace(/ \/$/, '');
                const itemMaterials = item.closest(".item-content").querySelector('[data-type="add-ons"]').textContent.trim().replace(/ \/$/, '');
                const itemSize = item.closest(".item-content").querySelector('[data-type="size"]').textContent.trim().replace(/ \/$/, '');

                // 檢查品名、冰塊、甜度、加料、尺寸是否相同
                if (
                    currentProduct.productName === itemProductName &&
                    currentProduct.ice === itemIce &&
                    currentProduct.sugar === itemSugar &&
                    currentProduct.materials === itemMaterials &&
                    currentProduct.size === itemSize &&
                    // 確保不是當前修改的商品(遍歷尋找的購物車商品和當前點擊的商品
                    item !== currentCartItem
                ){
                    const existingQuantity = parseInt(item.closest(".item-content").querySelector('[data-type="quantity"]').textContent);
                    // console.log("existingQuantity : ",existingQuantity)
                    // console.log("newQuantity : ",newQuantity)
                    const totalQuantity = existingQuantity + newQuantity;
                    //更新當前購物車一模一樣品項的數項
                    item.closest(".item-content").querySelector('[data-type="quantity"]').textContent = `${totalQuantity} 杯`;
                    //刪除當前編輯的商品(數量已累計可移除)
                    itemContent.remove();
                }
            })
                //編輯過商品同步修改localstorage
                updateLocalStorage();
                lightbox_el.style.display = "none";
                document.body.style.overflow = 'auto';
                document.body.style.paddingRight = '0px';
                // 清除全局變數
                currentCartItem = null;

        }
    //F11 判斷冰塊
      function getIceOption(Item) {
        if (Item.normal_ice) return '正常冰';
        if (Item.less_ice) return '少冰';
        if (Item.ice_free) return '去冰';
        if (Item.light_ice) return '微冰';
        if (Item.room_temperature) return '常溫';
        if (Item.hot) return '熱飲';
        return '未選擇';
      }
    //F12 判斷甜度
      function getSugarOption(Item) {
        if (Item.full_sugar) return '全糖';
        if (Item.less_sugar) return '少糖';
        if (Item.half_sugar) return '半糖';
        if (Item.quarter_sugar) return '微糖';
        if (Item.no_sugar) return '無糖';
        return '未選擇';
      }
    //F13 判斷加料
      function getAddOns(Item) {
        if (Item.pearl) return '加珍珠';
        if (Item.pudding) return '加布丁';
        if (Item.coconut_jelly) return '加椰果';
        if (Item.taro) return '加芋頭';
        if (Item.herbal_jelly) return '加仙草';
        return '無加料';
      }

      //F09 更新localstorage
      //renderproductdetail->handlelightbox->updateLocalStorage
        function updateLocalStorage() {
            //先抓目前頁面上所有商品項
            const cartItems = document.querySelectorAll(".cart-item");
            const cartData = [];
            //每個抓到商品ID，甜度冰塊尺寸杯數等資訊存到陣列中
            cartItems.forEach(item => {
                const productDetail = item.closest(".item-content").querySelector(".product-detail");

                const itemData = {
                    productId: Number(item.dataset.productId),
                    // 確保所有的冰塊、甜度和加料選項轉換為數字 (TINYINT 風格)
                    normal_ice: Number(productDetail.querySelector('[data-type="ice"]').textContent.includes('正常冰') ? 1 : 0),
                    less_ice: Number(productDetail.querySelector('[data-type="ice"]').textContent.includes('少冰') ? 1 : 0),
                    ice_free: Number(productDetail.querySelector('[data-type="ice"]').textContent.includes('去冰') ? 1 : 0),
                    light_ice: Number(productDetail.querySelector('[data-type="ice"]').textContent.includes('微冰') ? 1 : 0),
                    room_temperature: Number(productDetail.querySelector('[data-type="ice"]').textContent.includes('常溫') ? 1 : 0),
                    hot: Number(productDetail.querySelector('[data-type="ice"]').textContent.includes('熱飲') ? 1 : 0),
                    full_sugar: Number(productDetail.querySelector('[data-type="sugar"]').textContent.includes('全糖') ? 1 : 0),
                    less_sugar: Number(productDetail.querySelector('[data-type="sugar"]').textContent.includes('少糖') ? 1 : 0),
                    half_sugar: Number(productDetail.querySelector('[data-type="sugar"]').textContent.includes('半糖') ? 1 : 0),
                    quarter_sugar: Number(productDetail.querySelector('[data-type="sugar"]').textContent.includes('微糖') ? 1 : 0),
                    no_sugar: Number(productDetail.querySelector('[data-type="sugar"]').textContent.includes('無糖') ? 1 : 0),
                    pearl: Number(productDetail.querySelector('[data-type="add-ons"]').textContent.includes('珍珠') ? 1 : 0),
                    pudding: Number(productDetail.querySelector('[data-type="add-ons"]').textContent.includes('布丁') ? 1 : 0),
                    coconut_jelly: Number(productDetail.querySelector('[data-type="add-ons"]').textContent.includes('椰果') ? 1 : 0),
                    taro: Number(productDetail.querySelector('[data-type="add-ons"]').textContent.includes('芋頭') ? 1 : 0),
                    herbal_jelly: Number(productDetail.querySelector('[data-type="add-ons"]').textContent.includes('仙草') ? 1 : 0),
                    size: productDetail.querySelector('[data-type="size"]').textContent.trim().replace(/ \/$/, ''),
                    quantity: parseInt(productDetail.querySelector('[data-type="quantity"]').textContent)
                };
                cartData.push(itemData);
            });
            //覆蓋現在localstorage資料
            localStorage.setItem('cartItems', JSON.stringify(cartData));
        }
        //訂單彙總項目更新
        function updateDetail(){

        }

        //F10表單填送:處理商品運送方式及取貨時間
        function checkreceivemethod(){
        //抓到目前自取或外送選擇
        const pickUpInput = document.querySelector('input[name="delivery"]:checked');
        //確認至少有選擇一個
        if (!pickUpInput) {
            Swal.fire({
                icon: 'warning',
                title: '請選擇取貨方式',
                text: '請選擇自取或外送。',
                confirmButtonText: '確定'
            });
            return;
        }
        //根據選項填充文字
        let receivingMethod = '';
        if (pickUpInput.classList.contains('pick-up-input')) {
            receivingMethod = '自取';
        } else if (pickUpInput.classList.contains('carry-out-radio')) {
            receivingMethod = '外送';
        }
        //如果選擇了外送，必須檢查地址是否已填寫
            let addressDetail = '';
            if (receivingMethod === '外送') {
                addressDetail = document.querySelector('.carry-out-address .address').value;
                if (!addressDetail.trim()) {
                    // 如果地址沒有填寫，顯示提示訊息
                    Swal.fire({
                        icon: 'warning',
                        title: '請輸入地址',
                        text: '選擇外送時，請輸入地址。',
                        confirmButtonText: '確定'
                    });
                    return; // 結束，不進行下一步
                }
            }
            //判斷有無填寫取貨時間
            if(!date_input_el.value) {
                // 使用 SweetAlert 顯示錯誤訊息
                Swal.fire({
                    icon: 'error',
                    title: '錯誤',
                    text: '請選擇取貨時間'
                });
                return;
            }
            // 將選擇的取貨方式和地址（如果有）取貨時間儲存到 localStorage
            const reciverMethodInfo = {
                method: receivingMethod,
                address: addressDetail,
                receivertime:date_input_el.value
            };
            localStorage.setItem('reciverMethodInfo', JSON.stringify(reciverMethodInfo));
        }
        //F14 獲得的優惠券動態生成選項
        function getcoupons(customerId){
            fetch(`/cart/getCoupon/${customerId}`)
                .then(response => response.json())
                .then(coupons => {
                    console.log("coupons : ",coupons)
                    coupons.forEach(coupon => {
                        let option = document.createElement('option');
                        option.value = coupon.couponId;
                        option.textContent = coupon.couponName;
                        option.setAttribute('data-discount', coupon.discount);
                        couponSelect_el.appendChild(option);
                    });
                })
                .catch(error => console.error('Error loading coupons:', error));
        }
        //F20 計算訂單金額
        function Calculatetotal(){

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


