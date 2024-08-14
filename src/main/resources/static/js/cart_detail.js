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

  let order_detail_container_el = document.getElementsByClassName("order-detail-container")[0];
  let btn_addtopurchase_el =document.getElementsByClassName("btn-addtopurchase")[0];
  let btn_checkout_el = document.getElementsByClassName("btn-checkout")[0];

  let step_content_el = document.getElementsByClassName("step-content")[0];
  let step_content2_el = document.getElementsByClassName("step-content2")[0];
  let step_content3_el = document.getElementsByClassName("step-content3")[0];
  //修改訂單商品
  let btn_edit_el = document.getElementsByClassName("edit-btn");
  let btn_delete_el = document.getElementsByClassName("delete-btn");
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
  let pick_up_input_el = document.getElementsByClassName("pick-up-input")[0];
  let carry_out_radio_el = document.getElementsByClassName("carry-out-radio")[0];
  let address_el = document.getElementsByClassName("address")[0];
  //時間選擇
  let picker_el = document.getElementsByClassName("pickuper")[0];

  //優惠使用
  let coupon_number_el = document.getElementsByClassName("coupon-number")[0];
  let select_coupon_input_el = document.getElementsByClassName("select-coupon-input")[0];
  
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
  //結帳流程-3
  let btn_backto_last_page2_el = document.getElementsByClassName("btn-backto-last-page2")[0];
  let btn_submit_order_el = document.getElementsByClassName("btn-submit-order")[0];
  let btn_modal_close_el = document.getElementsByClassName("btn_modal_close")[0];

  //一進到結帳選擇頁面先get商家圖片及店名使用者
//   const productId = 1; // 這個ID應該根據實際情況獲取
//   const customerId = 1;
  //商家
//     fetch(`/api/getStoreInfo?productId=${productId}`)
//         .then(response => response.json())
//         .then(data => {
//             store_info_el.href=data.storeurl;
//             store_name_el.textContent = data.storename;
//             store_img_el.src = data.storeimgurl;
//         })
//         .catch(error => console.error('Error fetching store data:', error));
  //使用者
// });fetch(`/api/getUsreInfo?userId=${userId}`)
//         .then(response => response.json())
//         .then(data => {
//             customer_img_el_img_el.src = data.customerimg;
            // customer_name_el.textContent=data.nickname;
//         })
//         .catch(error => console.error('Error fetching store data:', error));
// });
  //********************************************第一頁按鈕事件*************************************
  //刪除、修改訂單
  //修改
  for(let i =0;i<btn_edit_el.length;i++){
    btn_edit_el[i].addEventListener("click",function() {
      lightbox_el.style.display = "flex";
      document.body.style.overflow = 'hidden';
      document.body.style.paddingRight = '17px';
      // 找到點擊的按鈕
      let button = this;
      let cartItem = button.closest(".cart-item");
      let productName = cartItem.querySelector(".product-name").textContent;
      let lightboxTitle = document.querySelector("#lightbox h1");
      lightboxTitle.textContent = productName;
      // function------提供商品選項
      // function fetchProductOptions(productId){
      //   fetch(`/getProductOptions?productName=${encodeURIComponent(productName)}`)
      //   .then(response => response.json())
      //   .then(data =>{
      //     let ice_el = document.querySelector('.ice + .lightbox-radio-group');
      //     let sugar_el = document.querySelector('.sugar + .lightbox-radio-group');
      //     let materials_el = document.querySelector('.materials + .lightbox-radio-group');
      //     data.ice.forEach(option => {
      //      <input type="radio" id="${option.id}" name="options" value="${option.value}">
      //      <label for="${option.id}">${option.label}</label>
      //     });
      //     data.sugar.forEach(option => {
      //       //      <input type="radio" id="${option.id}" name="options" value="${option.value}">
      //       //      <label for="${option.id}">${option.label}</label>
      //       //     });
      //     data.materials.forEach(option => {
      //            <input type="radio" id="${option.id}" name="options" value="${option.value}">
      //            <label for="${option.id}">${option.label}</label>
      //          });
      //   });
      // }
      btn_modal_close_el.addEventListener("click", function () {
        lightbox_el.style.display = "none";
        document.body.style.overflow = 'auto';
        document.body.style.paddingRight = '0px';
      })
      lightbox_el.addEventListener("click", function () {
        lightbox_el.style.display = "none";
        document.body.style.overflow = 'auto';
        document.body.style.paddingRight = '0px';
      });
      lightbox_el.querySelector("article").addEventListener("click", function (e) {
        e.stopPropagation();
      });
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
        console.log(qty_el.value);
        e.stopPropagation();
      })
      //刪除訂餐
      var currentItem = null;
      for (let i = 0; i < btn_delete_el.length; i++) {
        btn_delete_el[i].addEventListener("click", function (e) {
          lightbox_delete_el.style.display = "flex";
          document.body.style.overflow = 'hidden';
          document.body.style.paddingRight = '17px';

          currentItem = this.closest(".item-content");
          var productName = this.closest(".cart-item").querySelector(".product-name").textContent;
          delete_detail_el.innerHTML = `刪除 <span class="highlight">${productName}</span> ?`;
        })
      }

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

      //取貨方式
      // pick_up_input_el.addEventListener("click",function(){
      //   if(carry_out_radio_el.checked){
      //      carry_out_radio_el.checked=false;
      //     }
      //     address_el.disabled=true;
      // })
      // carry_out_radio_el.addEventListener("click",function(){
      //   if(pick_up_input_el.checked){
      //     pick_up_input_el.checked=false;
      //    }
      //    address_el.disabled = false;
      // })

      pick_up_input_el.addEventListener("click", function () {
        address_el.disabled = true;
      })
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
      //時間選擇
      picker_el.addEventListener("focus", function () {
        picker_el.classList.add("focus-border");
      })
      picker_el.addEventListener("blur", function () {
        picker_el.classList.remove("focus-border");
      });

      //優惠使用
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
        checksava_vehicle_el.disabled = true;
        checksava_vehicle_el.checked = false;
      })
      select_vehicle_el.addEventListener("click", function () {
        checksava_vehicle_el.disabled = false;
        vehicle_number_el.disabled = false;
        vehicle_number_el.focus();
      })
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
    })