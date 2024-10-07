document.addEventListener("DOMContentLoaded",function(){
    //****************************************************控制購物流程階段變換及頁面跳轉****************************************
    //元素綁定
    //購物流程進度條
    let step1_el = document.getElementById("step1");
    let step2_el = document.getElementById("step2");
    let step3_el = document.getElementById("step3");
    //結帳流程-1
    let cart
    let groupedItems = [];
    //商家及使用者
    let store_info_el = document.getElementsByClassName("store-info")[0];
    let store_img_el = document.getElementsByClassName("store-img")[0];
    let store_name_el = document.getElementsByClassName("store-name")[0];
    let customer_img_el = document.getElementsByClassName("customer-img")[0];
    let customer_name_el = document.getElementsByClassName("customer-name")[0];
    let customerId;
    let loginMember;
    let storeId;
    let nowstore;
    let customerCouponsId;
    let loyaltyCardId;
    let orderloyaltyCardId
    let ordernote;

    //訂單細項
    let item_detail_el = document.getElementsByClassName('item-detail')[0];

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

    //取貨方式
    let pick_up_el = document.getElementsByClassName('pick-up')[0];
    let pick_up_input_el = document.getElementsByClassName("pick-up-input")[0];
    // let carry_out_radio_el = document.querySelector('.carry-out-radio');
    let recevie_time_el = document.getElementsByClassName('recevie-time')[0];
    //時間選擇
    let date_input_el = document.getElementById('myDatepicker');

    //優惠使用
    // let coupon_number_el = document.getElementsByClassName("coupon-number")[0];
    let select_coupon_input_el = document.getElementsByClassName("select-coupon-input")[0];
    let select_membercard_input_el = document.getElementsByClassName("select-membercard-input")[0];
    let select_moneybag_input_el = document.getElementsByClassName("select-moneybag-input")[0];
    let moneybag_discount_number_el = document.getElementsByClassName("moneybag-discount-number")[0];
    let couponDropdown_el = document.getElementsByClassName('coupon-dropdown')[0];
    let couponSelect_el = document.getElementById('coupon-select');
    let membercard_count_el = document.getElementsByClassName('membercard-count')[0];
    let membercard_number_el = document.getElementById('membercard-number');
    let moneybag_count_el = document.getElementsByClassName('moneybag-count')[0]
    let moneybag_number_el = document.getElementById('moneybag-number') ;
    let couponSelected = false;
    let memberCardSelected = false;
    let moneyBagSelected = false;

    //訂單彙總
    let product_amount_el = document.getElementsByClassName('product-amount')[0];
    let product_unit_el = document.getElementsByClassName('product-unit')[0];
    let coupon_minus_number_el = document.getElementsByClassName('coupon-minus-number')[0];
    let membercard_minus_number_el = document.getElementsByClassName('membercard-minus-number')[0];
    let moneybag_minus_number_el = document.getElementsByClassName('moneybag-minus-number')[0];
    let last_totalAmount; //綠界支付金額
    let totalAmount; //訂單實際金額
    let totalquanity;
    let productamount;
    let invoiceMethod;
    let receiverMethod;
    let invoiceCarrier;
    let invoiceVat;

    let platform_fee_number_el = document.getElementsByClassName('platform-fee-number')[0];
    let CouponAmountText=null;
    let CouponAmount=null;
    let MemberCardAmountText=null;
    let MemberCardAmount=null;
    let MoneyBagAmountText=null;
    let MoneyBagAmount;
    let PlatformfeeText;
    let PlatformAmount;

    let total_amount_el = document.getElementsByClassName('total-amount')[0];

    //結帳流程-2
    //商品數目
    let payable_unit_el = document.getElementsByClassName('payable-unit')[0];
    let payable_price_el = document.getElementsByClassName('payable-price')[0];
    //取貨人
    let btn_backto_last_page_el = document.getElementsByClassName("btn-backto-last-page")[0];
    let btn_goto_next_page_el = document.getElementsByClassName("btn-goto-next-page")[0];
    let pickuper_el = document.getElementsByClassName('pickuper')[0];
    let select_cellphone_el = document.getElementsByClassName("select-cellphone")[0];
    let input_cellphone_el = document.getElementsByClassName("input-cellphone")[0];
    let cellphoneError_el = document.getElementById('cellphone-error');
    let select_phone_el = document.getElementsByClassName("select-phone")[0];
    let input_phone_zone_el = document.getElementsByClassName("input-phone-zone")[0];
    let input_phone_number_el = document.getElementsByClassName("input-phone-number")[0];
    let phoneZoneError_el = document.getElementById('phone-zone-error');
    let vehicle_number_error = document.getElementById('vehicle-number-error');
    // 台灣市話區碼列表 (部分常見區碼)
    let validPhoneZones = ['02', '03', '04', '05', '06', '07', '08'];
    let text2store_el = document.getElementsByClassName("text2store")[0];
    //發票方式
    let select_paper_el = document.getElementsByClassName("select-paper")[0];
    let select_vehicle_el = document.getElementsByClassName("select-vehicle")[0];
    let checksava_vehicle_el = document.getElementsByClassName("checksava-vehicle")[0];
    let vehicle_number_el = document.getElementsByClassName("vehicle-number")[0];
    let select_paper_uniform_el = document.getElementsByClassName("select-paper-uniform")[0];
    let uniform_numbers_el = document.getElementsByClassName("uniform-numbers")[0];
    let uniform_numbers_error_el = document.getElementById('uniform-numbers-error');

    //結帳流程-3
    let store_el = document.getElementById('store');
    let customer_el = document.getElementById('customer');
    let phone_el = document.getElementById('phone');
    let pickupMethod_el = document.getElementById('pickupMethod');
    let final_address_el = document.getElementById('final-address');
    let pickupTime_el = document.getElementById('pickupTime');
    let paymentMethod_el = document.getElementById('paymentMethod');
    let invoiceType_el = document.getElementById('invoiceType');
    let final_amount_el = document.getElementById('final-amount');
    let remark_el = document.getElementById('remark')
    let charLimit = 60;  // 設定字數限制

    let btn_backto_last_page2_el = document.getElementsByClassName("btn-backto-last-page2")[0];
    let btn_submit_order_el = document.getElementsByClassName("btn-submit-order")[0];
    let btn_modal_close_el = document.getElementsByClassName("btn_modal_close")[0];

    //一進到結帳選擇頁面先用localstorage抓DB以及獲取sessionstorage目前登入用戶ID
    //TODO 獲得用戶ID以抓取資料渲染
    const customer = sessionStorage.getItem('memberData');
    if(customer){
        let customerData = JSON.parse(customer);
        customerId = customerData.data.customerId;
        // console.log("customerId:",customerId)
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
                        text: '查無此會員資料 ID='+customerId,
                        icon: 'error'
                    });
                }
                console.log('Member Details:', member);
                //將取得會員資訊儲存
                loginMember = member;
                //放入會員資料
                customer_name_el.textContent = member.nickname;
                if(member.customerImg){
                    const userimageFormat = detectImageFormat(member.customerImg);
                    customer_img_el.src = `data:image/${userimageFormat};base64,${member.customerImg}`;
                }
            })
            .catch(error => {
                Swal.fire({
                    title: '取得會員數據異常',
                    text: error.message,
                    icon: 'error'
                }).then(() => {
                    history.back();
                });
            })
    }else{
        // 當找不到 memberData 時，顯示錯誤訊息
        Swal.fire({
            title: '會員資料缺失',
            text: '請登入',
            icon: 'info'
        }).then(() => {
            // 跳轉回登入頁面或執行其他行為
            window.location.href = '/login.html';
        });
    }

    //獲得localstorage資料(購物車)
    const cartitem = JSON.parse(localStorage.getItem('cart')) || [];
    console.log("cart:",cartitem)
    //先轉換cart&購物車分類
    reverseCartItems(cartitem)
    //localstorage購物車資料分類
    // const groupedItems = sortCartItems(cart);
    // console.log('分類後購物車商品列表: ',groupedItems)
    // 提取所有商品的 productId用來渲染網頁，一個ID只抓一次
    const productIds = [...new Set(groupedItems.map(item => item.productId))];
    //獲得商品ID列獲得的商品，並在方法中渲染前端頁面
    findproductbyid(productIds);

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
    //自取選項
    pick_up_input_el.addEventListener('click',function (){
        receiverMethod = 1;
        pickupMethod_el.textContent ="自取";
        console.log("address :  ",nowstore.storeAddress)
        final_address_el.textContent = nowstore.storeAddress;
    })

    //優惠使用(優惠券、會員卡、會員錢包)
    //獲得使用者優惠券選項
    select_coupon_input_el.addEventListener("click", function () {
        if (couponSelected) {
            // 取消選擇優惠券
            couponSelect_el.disabled = true;
            couponSelect_el.innerHTML = '<option disabled selected >請選擇優惠券</option>';
            coupon_minus_number_el.textContent = '$0';
            couponSelected = false;
            select_coupon_input_el.checked=false;
        } else {
            // 選擇優惠券
            couponSelect_el.disabled = false;
            membercard_count_el.style.display = "none";
            moneybag_count_el.style.display = "none";
            moneybag_discount_number_el.disabled=true;
            moneybag_discount_number_el.value = '';
            membercard_minus_number_el.textContent = '$0';
            moneybag_minus_number_el.textContent = '$0';
            getcoupons(loginMember.customerId);
            // 更新狀態
            couponSelected = true;
            memberCardSelected = false;
            moneyBagSelected = false;
        }
        // couponSelect_el.disabled=false;
        // membercard_count_el.style.display = "none";
        // moneybag_count_el.style.display= "none";
        // moneybag_discount_number_el.value ='';
        // membercard_minus_number_el.textContent = '$0';
        // moneybag_minus_number_el.textContent = '$0';
        // getcoupons(loginMember.customerId);
    })

    //點選優惠券時動態更新結帳明細折抵金額
    couponSelect_el.addEventListener('change',function (){
        let selectedOption = couponSelect_el.options[couponSelect_el.selectedIndex];
        let discount = selectedOption.getAttribute('data-discount');
        customerCouponsId = selectedOption.value;
        coupon_minus_number_el.textContent = `$${discount}`;
    })

    //加入點擊事件get會員卡點數
    select_membercard_input_el.addEventListener("click",function (){
        if (memberCardSelected) {
            // 取消會員卡
            membercard_count_el.style.display = "none";
            membercard_minus_number_el.textContent = '$0';
            memberCardSelected = false;
            select_membercard_input_el.checked=false;
        } else {
            // 使用會員卡
            GetMemberCardPoint(storeId, customerId);
            membercard_count_el.style.display = "flex";
            moneybag_count_el.style.display = "none";
            moneybag_discount_number_el.value = '';
            couponSelect_el.innerHTML = '<option disabled selected >請選擇優惠券</option>';
            couponSelect_el.disabled = true;
            moneybag_discount_number_el.disabled=true;
            coupon_minus_number_el.textContent = '$0';
            moneybag_minus_number_el.textContent = '$0';
            memberCardSelected = true;
            couponSelected = false;
            moneyBagSelected = false;
        }
        // GetMemberCardPoint(storeId,customerId);
        // membercard_count_el.style.display = "flex";
        // moneybag_count_el.style.display= "none";
        // moneybag_discount_number_el.value ='';
        // //清除優惠券欄位及會員卡優惠
        // couponSelect_el.innerHTML = '<option disabled selected >請選擇優惠券</option>';
        // couponSelect_el.disabled=true;
        // coupon_minus_number_el.textContent = '$0';
        // moneybag_minus_number_el.textContent = '$0';
    })
    //加入點擊事件get會員錢包餘額
    select_moneybag_input_el.addEventListener("click",function (){
        if (moneyBagSelected) {
            // 取消會員錢包
            moneybag_count_el.style.display = "none";
            moneybag_minus_number_el.textContent = '$0';
            moneybag_discount_number_el.value = '';
            moneybag_discount_number_el.disabled=true;
            select_moneybag_input_el.checked=false;
            moneyBagSelected = false;
        } else {
            // 使用會員錢包
            moneybag_count_el.style.display = "flex";
            if (loginMember.customerMoney != null) {
                if (loginMember.customerMoney <= 0) {
                    moneybag_count_el.textContent = `會員錢包餘額為 ${loginMember.customerMoney}元，無法扣除`;
                    couponSelected = false;
                    memberCardSelected = false;
                    moneyBagSelected = true;
                } else {
                    moneybag_count_el.textContent = `會員錢包餘額為 ${loginMember.customerMoney}元`;
                    moneybag_discount_number_el.disabled = false;
                    moneybag_discount_number_el.focus();
                    moneybag_discount_number_el.max = loginMember.customerMoney;
                    couponSelected = false;
                    memberCardSelected = false;
                    moneyBagSelected = true;
                }
            } else {
                moneybag_count_el.textContent = '無資料....';
            }
            membercard_count_el.style.display = "none";
            couponSelect_el.innerHTML = '<option disabled selected >請選擇優惠券</option>';
            couponSelect_el.disabled = true;
            coupon_minus_number_el.textContent = '$0';
            membercard_minus_number_el.textContent = '$0';
            moneyBagSelected = true;
        }
        // moneybag_count_el.style.display = "flex";
        // if(loginMember.customerMoney!=null){
        //     if(loginMember.customerMoney === 0){
        //         moneybag_count_el.textContent = `會員錢包餘額為 ${loginMember.customerMoney}元，無法扣除`
        //     }else{
        //         moneybag_count_el.textContent = `會員錢包餘額為 ${loginMember.customerMoney}元`
        //         moneybag_discount_number_el.disabled =false;
        //         moneybag_discount_number_el.focus();
        //         moneybag_discount_number_el.max=loginMember.customerMoney;
        //     }
        // }else{
        //     moneybag_count_el.textContent = '無資料....'
        // }
        // membercard_count_el.style.display = "none";
        // //清除優惠券及會員卡優惠
        // couponSelect_el.innerHTML = '<option disabled selected >請選擇優惠券</option>';
        // couponSelect_el.disabled=true;
        // coupon_minus_number_el.textContent = '$0';
        // membercard_minus_number_el.textContent = '$0';
    })
    //會員錢包輸入框事件
    moneybag_discount_number_el.addEventListener("focus", function () {
        moneybag_discount_number_el.placeholder = "";
    });
    moneybag_discount_number_el.addEventListener("blur", function () {
        if (moneybag_discount_number_el.value === "") {
            moneybag_discount_number_el.placeholder = "請輸入折抵金額";
        }
    });
    moneybag_discount_number_el.addEventListener('input',function (){
        if (moneybag_discount_number_el.value > loginMember.customerMoney) {
            moneybag_discount_number_el.value = loginMember.customerMoney; // 如果輸入值大於錢包餘額，自動設為最大值
        }
        if (moneybag_discount_number_el.value < 1) {
            moneybag_discount_number_el.value = 1; // 如果輸入值小於1，自動設為1
        }
        moneybag_minus_number_el.textContent=`$${moneybag_discount_number_el.value}`;
        Calculatetotal();
    })

    //頁面跳轉
    btn_addtopurchase_el.addEventListener("click", function (e) {
        location.href=`productMenu.html?storeId=${storeId}`;
    })

    //進入下一頁、檢查欄位有無輸入
    btn_checkout_el.addEventListener("click", function (e) {
        //檢查是否至少選一個取貨方式
        //選取所有具有 name="delivery" 的 radio 按鈕
        let deliveryOptions = document.querySelectorAll('input[name="delivery"]');
        let isSelected = false;
        deliveryOptions.forEach(option=> {
            if (option.checked) {
                isSelected = true;
            }
        })
        if(!isSelected){
            Swal.fire({
                icon: 'error',
                title: '未選擇配送方式',
                text: '請選擇一個配送選項！',
                confirmButtonText: '確定',
            })
            return;
        }
        //判斷是選了自取、外送(自填)、外送預設地址哪一個
        let carry_out_radio_el = document.querySelector('.carry-out-radio');
        let address_el = document.querySelector('.address');
        let carryout_default_input_el = document.querySelector('.carryout-default-input');
        if(pick_up_input_el.checked){
            console.log("選定自取")
        }else if(carry_out_radio_el.checked){
            if(address_el.value===''){
                Swal.fire({
                    icon: 'error',
                    title: '未填寫地址',
                    text: '請填寫地址！',
                    confirmButtonText: '確定',
                })
                return;
            }else{
                console.log("外送地址 : ",address_el.value)
            }
        }else if(carryout_default_input_el.checked){
            let memberaddress = document.querySelectorAll('input[name="member-address"]');
            let addressisSelected = false;
            //檢查是不是有選預設地址
            memberaddress.forEach(option=>{
                if(option.checked){
                    addressisSelected = true;
                }
            })
            if(!addressisSelected){
                Swal.fire({
                    icon: 'error',
                    title: '未選擇地址',
                    text: '請選擇一個預設地址！',
                    confirmButtonText: '確定',
                })
                return;
            }
            // //正確選擇預設地址後，獲得選取的項目
            // let selectedRadio = document.querySelector('input[name="member-address"]:checked');
            // //獲得選取的子選項label
            // let selectedLabel = document.querySelector(`label[for="${selectedRadio.id}"]`);
            // console.log("選擇的預設地址",selectedLabel.textContent)
        }
        //檢查選取取貨時間
        if(date_input_el.value ==='') {
            Swal.fire({
                icon: 'error',
                title: '錯誤',
                text: '請選擇取貨時間'
            });
            return;
        }

        //保存了第一頁資訊切換到第二頁
        payable_unit_el.textContent = product_unit_el.textContent;
        payable_price_el.textContent = total_amount_el.textContent;
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
    //計算總額
    document.addEventListener('click',Calculatetotal)
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
        onShow: function() {
            // 禁止手動輸入
            document.querySelector('#myDatepicker').setAttribute('readonly', true);
        },
        minDate: new Date(), // 設定不能選擇早於當前的日期
        timepicker: true, // 開啟時間選擇功能
        minutesStep: 1, // 設定分鐘選擇間隔
        minHours: 9,
        maxHours: 20

    });

    //********************************************第二頁按鈕事件*************************************
    //取貨人資料
    select_cellphone_el.addEventListener("click", function () {
        input_cellphone_el.disabled = false;
        input_phone_zone_el.disabled = true;
        input_phone_number_el.disabled = true;
        input_phone_zone_el.value = "";
        input_phone_number_el.value = "";
        phoneZoneError_el.style.display = 'none';
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
    //檢查手機輸入格式
    input_cellphone_el.addEventListener('input', function () {
        let cellphoneInput = this.value;
        // 台灣手機號碼格式檢核 (09 開頭，接 8 位數字)
        let cellphoneRegex = /^09\d{8}$/;
        if (cellphoneRegex.test(cellphoneInput)) {
            // 符合格式，隱藏錯誤提示
            cellphoneError_el.style.display = 'none';
            input_cellphone_el.style.borderColor = '';
        } else {
            // 不符合格式，顯示錯誤提示
            cellphoneError_el.style.display = 'inline';
        }
    });
    //市話
    select_phone_el.addEventListener("click", function () {
        input_phone_zone_el.disabled = false;
        input_cellphone_el.disabled = true;
        input_cellphone_el.value = "";
        cellphoneError_el.style.display = 'none';
        input_cellphone_el.style.borderColor = '';
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
    //檢查市話區碼
    input_phone_zone_el.addEventListener('input', function () {
        let phoneZoneInput = this.value;
        // 檢核輸入的區碼是否有效
        if (validPhoneZones.includes(phoneZoneInput)) {
            // 符合區碼，隱藏錯誤提示
            phoneZoneError_el.style.display = 'none';
            // 如果輸入的區碼長度為2，解鎖市話號碼輸入框並自動將焦點設置到該框
            if (phoneZoneInput.length === 2) {
                //避免太快跳轉過去會將鍵盤輸入文字誤帶過去
                setTimeout(function () {
                    input_phone_number_el.disabled = false;
                    input_phone_number_el.focus();
                    input_phone_number_el.value='';
                }, 10);
            }
        } else {
            // 不符合區碼，顯示錯誤提示
            phoneZoneError_el.style.display = 'inline';
            // 禁用市話號碼輸入框
            input_phone_number_el.disabled = true;
        }
    });
    input_phone_number_el.addEventListener('input', function () {
        let phoneInput = this.value;
        let phoneRegex = /^\d{7}$/;
        if (phoneRegex.test(phoneInput)) {
            // 符合格式，隱藏錯誤提示
            phoneZoneError_el.style.display = 'none';
        } else {
            phoneZoneError_el.style.display = 'inline';
        }
    });

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
        invoiceMethod =2;
        vehicle_number_el.disabled = true;
        uniform_numbers_el.disabled= true;
        // checksava_vehicle_el.disabled = true;
        // checksava_vehicle_el.checked = false;
        uniform_numbers_el.value="";
        uniform_numbers_error_el.style.display="none";
        vehicle_number_el.value="";
        vehicle_number_error.style.display = 'none';
    })
    select_vehicle_el.addEventListener("click", function () {
        // checksava_vehicle_el.disabled = false;
        invoiceMethod =1;
        vehicle_number_el.disabled = false;
        vehicle_number_el.value = loginMember.customerCarrier;
        uniform_numbers_el.disabled= true;
        vehicle_number_el.focus();
        uniform_numbers_el.value="";
        uniform_numbers_error_el.style.display="none";
    })
    select_paper_uniform_el.addEventListener("click",function (){
        invoiceMethod = 3;
        uniform_numbers_el.disabled=false;
        vehicle_number_el.disabled = true;
        // checksava_vehicle_el.disabled = true;
        // checksava_vehicle_el.checked = false;
        uniform_numbers_el.focus();
        vehicle_number_el.value="";
        vehicle_number_error.style.display = 'none';
    })
    uniform_numbers_el.addEventListener("focus", function () {
        uniform_numbers_el.placeholder = "";
    });
    uniform_numbers_el.addEventListener("blur", function () {
        if (uniform_numbers_el.value === "") {
            uniform_numbers_el.placeholder = "請輸入統編";
        }
    });
    //統編驗證
    uniform_numbers_el.addEventListener("input",function (){
        if(vatCheck(this.value)){
            uniform_numbers_error_el.style.display="none";
        }else{
            uniform_numbers_error_el.style.display="inline";
        }
    })
    //載具驗證
    vehicle_number_el.addEventListener('input',function (){
        let carrierPattern = /^\/[A-Z0-9+\-\.]{7}$/;
        if(carrierPattern.test(this.value)){
            vehicle_number_error.style.display = 'none';
        }else{
            vehicle_number_error.style.display = 'inline';
        }
    })
    //頁面跳轉
    btn_backto_last_page_el.addEventListener("click", function (e) {
        step2_el.classList.remove("active");
        step1_el.classList.add("active");
        if (step_content2_el.style.display === "flex") {
            step_content2_el.style.display = "none";
            step_content_el.style.display = "flex";
            order_detail_container_el.scrollIntoView({
                behavior: "smooth" // 使用平滑滾動效果
            });
        }
        e.stopPropagation()
    })

    btn_goto_next_page_el.addEventListener("click", function (e) {
        //檢查欄位是不是都正確填寫
        //取貨人
        if(pickuper_el.value ===''){
            Swal.fire({
                icon: 'error',
                title: '未填寫取貨人姓名',
                text: '請填寫取貨人姓名！',
                confirmButtonText: '確定',
            })
            return;
        }
        //聯絡電話
        let selectcontact = document.querySelectorAll('input[name="select-contact"]');
        let contactisSelected = false;
        //檢查是不是有選聯絡方式
        selectcontact.forEach(option=>{
            if(option.checked){
                contactisSelected = true;
            }
        })
        if(!contactisSelected){
            Swal.fire({
                icon: 'error',
                title: '未選擇連絡電話',
                text: '請選擇一個連絡方式！',
                confirmButtonText: '確定',
            })
            return;
        }
        //檢查手機
        if(select_cellphone_el.checked){
            if(input_cellphone_el.value===''||cellphoneError_el.style.display==='inline'){
                Swal.fire({
                    icon: 'error',
                    title: '手機號碼錯誤',
                    text: '請填寫正確手機號碼！',
                    confirmButtonText: '確定',
                })
                return;
            }
        }
        //檢查電話
        if(select_phone_el.checked){
            if(input_phone_zone_el.value ===''||
                input_phone_number_el.value ===''||
                phoneZoneError_el.style.display==='inline'){
                Swal.fire({
                    icon: 'error',
                    title: '市話號碼錯誤',
                    text: '請填寫正確市話號碼！',
                    confirmButtonText: '確定',
                })
                return;
            }
        }
        //發票方式
        let selectinvoice = document.querySelectorAll('input[name="select-invoice"]');
        let invoiceisSelected = false;
        //檢查是不是有選發票方式
        selectinvoice.forEach(option=>{
            if(option.checked){
                invoiceisSelected = true;
            }
        })
        if(!invoiceisSelected){
            Swal.fire({
                icon: 'error',
                title: '未選擇發票方式',
                text: '請選擇一個發票方式！',
                confirmButtonText: '確定',
            })
            return;
        }
        if(select_paper_el.checked){
            //預計將金流方式設定為電子發票
        }
        if(select_paper_uniform_el.checked){
            //預計將金流方式設定為紙本發票加統編
            if(uniform_numbers_el.value ===''||uniform_numbers_error_el.style.display==='inline'){
                Swal.fire({
                    icon: 'error',
                    title: '統編號碼錯誤',
                    text: '請填寫正確統編號碼！',
                    confirmButtonText: '確定',
                })
                return;
            }
        }
        //驗證載具
        if(select_vehicle_el.checked){
            if(vehicle_number_el.value ==='' ||vehicle_number_error.style.display==='inline'){
                Swal.fire({
                    icon: 'error',
                    title: '載具號碼錯誤',
                    text: '請填寫載具！',
                    confirmButtonText: '確定',
                })
                return;
            }
        }
        step2_el.classList.remove("active");
        //*****************************訂單明細*********************************
        //店家
        store_el.textContent= nowstore.storeName;
        //取貨人
        customer_el.textContent = pickuper_el.value;
        //連絡電話
        if(select_cellphone_el.checked && input_cellphone_el.value !==''){
            phone_el.textContent = input_cellphone_el.value;
        }else if(select_phone_el.checked && input_phone_zone_el.value !=='' &&
            input_phone_number_el.value !==''){
            phone_el.textContent = input_phone_zone_el.value + input_phone_number_el.value;
        }
        //取貨方式
        if(pickupMethod_el.textContent ==="外送"){
            let select_carry_out = document.querySelector('.carry-out-radio');
            let select_address = document.querySelector('.address');
            let select_carry_out_default = document.querySelector('.carryout-default-input');
            let selected_address = document.querySelector('input[name="member-address"]:checked');
            if(select_carry_out && select_carry_out.checked && select_address.value !==''){
                final_address_el.textContent = select_address.value
            }else if(select_carry_out_default && select_carry_out_default.checked){
                let label_address = document.querySelector(`label[for="${selected_address.id}"]`);
                final_address_el.textContent = label_address.textContent;
            }else {
                final_address_el.textContent = "未選擇地址";
            }
        }
        //取貨時間
        pickupTime_el.textContent = date_input_el.value;
        //開立發票方式
        if(select_paper_el.checked){
            invoiceType_el.textContent = "電子發票"
        }else if(select_paper_uniform_el.checked){
            invoiceType_el.textContent = "電子發票(含統一編號)"
        }else if(select_vehicle_el.checked){
            invoiceType_el.textContent = "手機載具"
        }
        //應付金額
        final_amount_el.textContent = total_amount_el.textContent;
        //備註
        if(text2store_el.value.length>charLimit){
            remark_el.textContent = text2store_el.value.substring(0, charLimit) + '...';  // 限制字數顯示
        } else {
            remark_el.textContent = text2store_el.value;  // 不超過限制時顯示完整內容
        }

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

    //********************************************第三頁*************************************
    // 當點擊 td 時展開/收起
    remark_el.addEventListener('click', function() {
        if (remark_el.textContent.endsWith('...')) {
            remark_el.textContent = text2store_el.value;  // 展開完整內容
        } else {
            remark_el.textContent = text2store_el.value.substring(0, charLimit) + '...';  // 回到限制字數的內容
        }
    });
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
    })

    //訂單轉綠界金流
    btn_submit_order_el.addEventListener('click',function (){
        //先建立空的orDetails預備放入所有商品明細
        const orderDetails = [];
        // 找到所有商品詳細資料
        const cartitems_el = document.querySelectorAll('.cart-item');
        console.log("cart-item",cartitems_el)
        cartitems_el.forEach(cartitem=>{
            let productId =cartitem.getAttribute('data-product-id');
            productId = parseInt(productId, 10);
            // 獲取 product-detail 的父元素
            let productDetail = cartitem.nextElementSibling; // 獲取下一個兄弟元素
            // 初始化變數
            let productTemperature = '';
            let productSugar = '';
            let productAddMaterials = '';
            let productPrice = '';
            let quantityNumber = 0;

            if (productDetail && productDetail.classList.contains('product-detail')) {
                // 冰塊
                let temperatureElement = productDetail.querySelector('[data-type="temperature"]');
                if (temperatureElement) {
                    productTemperature = temperatureElement.textContent.trim().replace(/\s*\/\s*/, '');
                    console.log('冰塊:', productTemperature);
                } else {
                    console.error('找不到 ice 節點');
                }
                // 甜度
                let sugarElement = productDetail.querySelector('[data-type="sweetness"]');
                if (sugarElement) {
                    productSugar = sugarElement.textContent.trim().replace(/\s*\/\s*/, '');
                    console.log('甜度:', productSugar);
                } else {
                    console.error('找不到 sugar 節點');
                }

                // 加料
                let addMaterialsElement = productDetail.querySelector('[data-type="toppings"]');
                if (addMaterialsElement) {
                    productAddMaterials = addMaterialsElement.textContent.trim().replace(/\s*\/\s*/, '');
                    console.log('加料:', productAddMaterials);
                } else {
                    console.error('找不到 materials 節點');
                }

                // 商品價格
                let priceElement = productDetail.querySelector('[data-type="price"]');
                if (priceElement) {
                    productPrice = parseInt(priceElement.textContent.trim().replace(/[^0-9]/g, ''), 10);
                    console.log('商品價格:', productPrice);
                } else {
                    console.error('找不到 productprice 節點');
                }

                // 數量
                let quantityElement = productDetail.querySelector('[data-type="buyNum"]');
                if (quantityElement) {
                    quantityNumber = parseInt(quantityElement.textContent.trim().match(/\d+/)[0], 10);
                    console.log('數量:', quantityNumber);
                } else {
                    console.error('找不到 quantityNumber 節點');
                }
            } else {
                console.error('找不到 product-detail 節點');
            }
            // 將每個商品的資料組成物件，並推入 orderDetails 陣列中
            orderDetails.push({
                // orderDetailId: ,
                // orderId: orderId,
                productId: productId,
                productSugar: productSugar,
                productTemperature: productTemperature,
                productAddMaterials: productAddMaterials,
                productQuantity: quantityNumber,
                productPrice: productPrice
            });
        })
        //建立存到order的JSON數據
        //優惠券會員卡ID(若有使用)
        if(select_membercard_input_el.checked && MemberCardAmount !== 0){
            orderloyaltyCardId = loyaltyCardId;
        }else{
            orderloyaltyCardId =null;
        }
        if(select_coupon_input_el.checked===false){
            customerCouponsId = null;
        }
        //準備載具號碼及統編
        if(vehicle_number_el.value===''){
            invoiceCarrier = null;
        }else{
            invoiceCarrier = vehicle_number_el.value
        }
        if(uniform_numbers_el.value===''){
            invoiceVat = null;
        }else{
            invoiceVat = parseInt(uniform_numbers_el.value)
        }
        //備註
        if(text2store_el.value ===''){
            ordernote =null;
        }else{
            ordernote =text2store_el.value;
        }
        //若金額為0元為開立發票+2元但註記不進行請款
        // if(totalAmount ===0){
        //     ordernote = (ordernote ? ordernote + "；" : "") + "僅開立發票用，不予請款";
        // }
        const orderData = {
            orders: {
                customerId: customerId,
                storeId:storeId,
                orderStatus: 1,
                customerCouponsId: customerCouponsId,
                loyaltyCardId:orderloyaltyCardId,
                couponDiscount: CouponAmount,
                loyaltyDiscount: MemberCardAmount,
                customerMoneyDiscount: MoneyBagAmount,
                orderProductQuantity: totalquanity,
                productAmount: productamount,
                paymentMethod: 1,
                paymentAmount: totalAmount,
                invoiceMethod: invoiceMethod,
                invoiceCarrier:invoiceCarrier,
                invoiceVat:invoiceVat,
                orderNote:ordernote,
                receiverMethod: receiverMethod,
                receiverName: customer_el.textContent,
                receiverPhone: phone_el.textContent,
                receiverDatetime: pickupTime_el.textContent.replace(/-/g, '/')
            },
            orderDetails: orderDetails , // 將動態生成的商品明細加入
            "disputeOrder": null
        };
        console.log("orders:",orderData);

        fetch('order/addOrder',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        }).then(response => {
                if (!response.ok) {
                    throw new Error('傳輸數據異常');
                }
                return response.json();
            })
            .then(data => {
                console.log('成功:', data);
                //把訂單編號存到Local 後續可以用，先移除(若有)
                localStorage.removeItem('orderId');
                localStorage.setItem('orderId', data.orders.orderId);
                //支付流程
                const params = new URLSearchParams();
                //若為0元，為開立發票+30元，不予用戶收款
                // if(last_totalAmount===0){
                //     last_totalAmount =30;
                // }
                params.append('TotalAmount', last_totalAmount); // 交易金額
                fetch('/payment/createorder', {
                    method: 'POST',
                    headers:{
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: params.toString()
                }).then(response => response.text()) // 因為後端返回的是 HTML 表單，所以需要使用 .text()
                    .then(htmlForm => {
                        // 創建一個空的 div 並將 HTML 表單插入
                        const formContainer = document.createElement('div');
                        formContainer.innerHTML = htmlForm;

                        // 將表單插入到當前頁面中
                        document.body.appendChild(formContainer);

                        // 自動提交表單
                        const form = formContainer.querySelector('form');
                        form.submit(); // 自動提交支付表單，跳轉到綠界
                    })
            })
            .catch(error => {
            console.error('發生錯誤:', error);
        })
    })
    //******** ************************************function區****************************************
    //F00 將cart 資料轉換
    function reverseCartItems(cart){
        const newcart = [];
        Object.keys(cart).forEach(productID=>{
            let product = cart[productID];
            newcart.push({
                productId: parseInt(productID),
                temperature: product.temperature,
                sweetness: product.sweetness,
                toppings: product.toppings,
                size: product.size,
                buyNum: product.buyNum
            })
        })
        console.log("new cart:",newcart)
        sortCartItems(newcart)
    }

    //F01 localstorage購物車資料分類
    function sortCartItems(cartItems){
        //將全部購物車項目依序讀出分類、累加
        cartItems.forEach(item => {
            //如果找到符合條件項目則返回該項目，沒有找到則返回undefined
            const existingItem = groupedItems.find(groupedItem =>
                groupedItem.productId === item.productId &&
                groupedItem.temperature === item.temperature &&
                groupedItem.sweetness === item.sweetness &&
                groupedItem.toppings === item.toppings &&
                groupedItem.size === item.size
            );
            //找到商品ID一樣甜度冰塊加料尺寸都一樣的就累加數量
            if (existingItem) {
                existingItem.buyNum += item.buyNum;
            } else {
                //沒有找到就存入新的項目，只存需要的欄位
                groupedItems.push({
                    productId: item.productId,
                    sweetness: item.sweetness,
                    temperature: item.temperature,
                    toppings: item.toppings,
                    size: item.size,
                    buyNum: item.buyNum
                });
            }
        });
        console.log("groupItems:",groupedItems)
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
                storeId = products[0].productStoreId;
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
                const errorMessage = error.message || '發生未知錯誤，請稍後再試。';
                Swal.fire({
                    title: '提醒',
                    text: errorMessage.replace(/Error: /, ''),
                    icon: 'info'
                }).then(() => {
                    // window.location.href = 'homePage.html'; // 跳轉到指定的網頁
                    history.back();
                })
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
                nowstore = store;
                //判斷現在是不是營業時間及是否接單(測試時可先註解)
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
                    const storeimageFormat = detectImageFormat(store.logo);
                    store_img_el.src = `data:image/${storeimageFormat};base64,${store.logo}`;
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
            //抓取標籤以便運用
            let carry_out_radio_el = document.getElementsByClassName("carry-out-radio")[0];
            let address_el = document.getElementsByClassName("address")[0];

            carry_out_radio_el.addEventListener("click", function () {
                receiverMethod = 2;
                address_el.disabled = false;
                address_el.focus();
                pickupMethod_el.textContent ="外送";
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
            //獲得用戶常用地址,若有則生成選項
            fetch(`/cart/checkoutlist/Memberaddress/${customerId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(address=> {
                    if (address && address.length > 0) {
                        // 資料存在，處理地址資料
                        console.log('User Address:', address);
                        //先建立外送(預設地址)選項
                        const carryoutdefalut = document.createElement('div');
                        carryoutdefalut.className ='carry-out-default';
                        carryoutdefalut.innerHTML+=`
                         <input class="carryout-default-input" type="radio" name="delivery">
                         <label class="carryout-default-label" for="pick-up-label" style="padding-top: 10px">
                         外送(預設地址)
                         </label>
                    `;
                        carryoutdiv.insertAdjacentElement('afterend', carryoutdefalut);
                        //建立外送(預設地址) 地址選項區塊
                        const suboptions = document.createElement('div')
                        suboptions.style.display="none";
                        suboptions.className = 'sub-options';
                        carryoutdefalut.appendChild(suboptions);
                        let sub_options_el = document.getElementsByClassName('sub-options')[0];
                        //在地址選項區塊生成每個子選項一個div區塊
                        address.forEach(data=>{
                            const addressItem = document.createElement('div');
                            addressItem.innerHTML = `
                            <input type="radio" name="member-address" id="address-${data.customerAddressId}">
                            <label for="address-${data.customerAddressId}">${data.customerAddress}</label>
                            `;
                            sub_options_el.appendChild(addressItem);  // 將每組 input 和 label 添加到 sub-options 中
                        })

                        //點擊外送(預設地址)展開子選項
                        let carryout_default_input_el = document.getElementsByClassName('carryout-default-input')[0];
                        carryout_default_input_el.addEventListener('click',function (){
                            receiverMethod = 2;
                            suboptions.style.display="block";
                            address_el.disabled = true;
                            address_el.value="";
                            pickupMethod_el.textContent ="外送";
                        })

                        // 點擊自取、外送(自填)選項關閉外送地址選項區塊
                        pick_up_input_el.addEventListener("click", function () {
                            suboptions.style.display = "none";  // 隱藏外送地址選項
                        })
                        carry_out_radio_el.addEventListener("click", function () {
                            suboptions.style.display = "none";
                            address_el.textContent="";
                        })
                    } else {
                        console.log('No address data found.');
                    }
                })
                .catch(error => console.error('Error loading coupons:', error));

            //針對預設地址選項以外的情形綁定事件
            //點擊自取選項關閉地址輸入框
            pick_up_input_el.addEventListener("click", function () {
                address_el.disabled = true;
                address_el.value='';
            })
        }
    }

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
              <div class="detail-item" data-type="sweetness">${item.sweetness} /</div>
              <div class="detail-item" data-type="temperature">${item.temperature} /</div>
              <div class="detail-item" data-type="toppings">${item.toppings} /</div>
              <div class="detail-item" data-type="price">$${product.productPrice}/ </div>
              <div class="detail-item" data-type="size">${item.size} /</div>
              <div class="detail-item" data-type="buyNum">${item.buyNum} 杯</div>
                `;
                //將明細放到商品資訊欄
                itemContent.appendChild(productDetail);
                //把彙整後商品資訊放到購物明細區塊
                item_detail_el.appendChild(itemContent);
                // 動態綁定 edit-btn 事件
                //TODO 鎖定這一次新建立的cartItem底下的編輯按鈕 ，點擊杯數加料要變更金額
                const editBtn = cartItem.querySelector('.edit-btn');
                editBtn.addEventListener('click',function (){
                    // 取得被點擊商品的 productId
                    let productId = this.closest('.cart-item').dataset.productId;
                    // 從 products 陣列中根據 productId 找到對應的商品資料
                    const product = products.find(p => p.productId === parseInt(productId));
                    //動態生成冰塊、甜度、加料選項
                    generateIceOptions(product);
                    generateSugarOptions(product);
                    generateAddOnsOptions(product);
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
        //渲染完商品項目後，初步計算總額
        Calculatetotal();
    }
    //F08 燈箱:編輯事件處理(在按下確認更新時觸發)
    function handlelightbox(){
        //先檢查全域變數是否存在cartItem
        if (!currentCartItem) return;
        const selectedIce = document.querySelector('input[name="ice-options"]:checked');
        const selectedSugar = document.querySelector('input[name="sugar-options"]:checked');
        const selectedMaterials = document.querySelector('input[name="materials-options"]:checked');
        // const selectedSize = document.querySelector('input[name="size-options"]:checked');
        const newQuantity = parseInt(qty_el.value);
        //確認冰塊、甜度、加料、尺寸皆有選
        if (!selectedIce || !selectedSugar || !selectedMaterials) {
            Swal.fire({
                icon: 'warning',
                title: '請選擇所有項目',
                text: '請選擇所有選項（冰塊、甜度、加料）。',
                confirmButtonText: '確定'
            });
            return;
        }
        //設定選中的選項文字
        const iceText = selectedIce.nextElementSibling.textContent;
        const sugarText = selectedSugar.nextElementSibling.textContent;
        const materialsText = selectedMaterials.nextElementSibling.textContent;
        // const sizeText = selectedSize.nextElementSibling.textContent;
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
            // size: sizeText
        };
        //更新頁面商品細項
        productDetail.querySelector('[data-type="temperature"]').textContent = `${iceText} / `;
        productDetail.querySelector('[data-type="sweetness"]').textContent = `${sugarText} / `;
        productDetail.querySelector('[data-type="toppings"]').textContent = `${materialsText} / `;
        // productDetail.querySelector('[data-type="size"]').textContent = `${sizeText} / `;
        productDetail.querySelector('[data-type="buyNum"]').textContent = `${newQuantity} 杯`;

        // 遍歷購物車中的商品，檢查是否有相同品名和選項的商品
        const cartItems = document.querySelectorAll(".cart-item");
        cartItems.forEach(item =>{
            const itemProductName = item.querySelector(".product-name").textContent;
            const itemIce = item.closest(".item-content").querySelector('[data-type="temperature"]').textContent.trim().replace(/ \/$/, '');
            const itemSugar = item.closest(".item-content").querySelector('[data-type="sweetness"]').textContent.trim().replace(/ \/$/, '');
            const itemMaterials = item.closest(".item-content").querySelector('[data-type="toppings"]').textContent.trim().replace(/ \/$/, '');
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
                const existingQuantity = parseInt(item.closest(".item-content").querySelector('[data-type="buyNum"]').textContent);
                // console.log("existingQuantity : ",existingQuantity)
                // console.log("newQuantity : ",newQuantity)
                const totalQuantity = existingQuantity + newQuantity;
                //更新當前購物車一模一樣品項的數項
                item.closest(".item-content").querySelector('[data-type="buyNum"]').textContent = `${totalQuantity} 杯`;
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

    //F11 判斷商品冰塊加入燈箱選項
    function generateIceOptions(product) {
        // 取得容器節點
        let iceContainer = document.querySelector('.lightbox-radio-group');
        // 清空原有的選項，避免重複生成
        iceContainer.innerHTML = '';

        // 定義每個選項的資料
        const iceOptions = [
            { id: 'normal_ice', value: 1, label: '正常冰', condition: product.normalIce },
            { id: 'less_ice', value: 2, label: '少冰', condition: product.lessIce },
            { id: 'ice_free', value: 3, label: '去冰', condition: product.iceFree },
            { id: 'light_ice', value: 4, label: '微冰', condition: product.lightIce },
            { id: 'room_temperature', value: 5, label: '常溫', condition: product.roomTemperature },
            { id: 'hot', value: 6, label: '熱飲', condition: product.hot }
        ];

        // 根據每個條件生成對應的選項
        iceOptions.forEach(option => {
            if (option.condition) {
                // 動態創建 input 和 label
                const input = document.createElement('input');
                input.type = 'radio';
                input.id = option.id;
                input.name = 'ice-options';
                input.value = option.value;

                const label = document.createElement('label');
                label.setAttribute('for', option.id);
                label.textContent = option.label;

                // 將 input 和 label 添加到容器中
                iceContainer.appendChild(input);
                iceContainer.appendChild(label);
            }
        });
    }

    //F12 根據商品資訊生成燈箱甜度選項
    function generateSugarOptions(product) {
        // 取得容器節點
        const sugarContainer = document.querySelectorAll('.lightbox-radio-group')[1];
        // 清空原有的選項，避免重複生成
        sugarContainer.innerHTML = '';

        // 定義每個甜度選項的資料
        const sugarOptions = [
            { id: 'full_sugar', value: 1, label: '全糖', condition: product.fullSugar },
            { id: 'less_sugar', value: 2, label: '少糖', condition: product.lessSugar },
            { id: 'half_sugar', value: 3, label: '半糖', condition: product.halfSugar },
            { id: 'quarter_sugar', value: 4, label: '微糖', condition: product.quarterSugar },
            { id: 'no_sugar', value: 5, label: '無糖', condition: product.noSugar }
        ];

        // 根據每個條件生成對應的選項
        sugarOptions.forEach(option => {
            if (option.condition) {
                // 動態創建 input 和 label
                const input = document.createElement('input');
                input.type = 'radio';
                input.id = option.id;
                input.name = 'sugar-options';
                input.value = option.value;

                const label = document.createElement('label');
                label.setAttribute('for', option.id);
                label.textContent = option.label;

                // 將 input 和 label 添加到容器中
                sugarContainer.appendChild(input);
                sugarContainer.appendChild(label);
            }
        });
    }

    //F13 根據商品資訊產生燈箱加料選項
    function generateAddOnsOptions(product) {
        // 取得加料的容器節點
        const addOnsContainer = document.querySelectorAll('.lightbox-radio-group')[2];
        // 清空原有的選項，避免重複生成
        addOnsContainer.innerHTML = '';

        // 定義每個加料選項的資料
        const addOnsOptions = [
            { id: 'pearl', label: '珍珠', condition: product.pearl },
            { id: 'pudding', label: '布丁', condition: product.pudding },
            { id: 'coconut_jelly', label: '椰果', condition: product.coconut_jelly },
            { id: 'taro', label: '芋圓', condition: product.taro },
            { id: 'herbal_jelly', label: '仙草', condition: product.herbal_jelly }
        ];
        // 先添加「無加料」選項
        const noAddOnInput = document.createElement('input');
        noAddOnInput.type = 'radio';
        noAddOnInput.id = 'no_add_ons';
        noAddOnInput.name = 'materials-options';
        noAddOnInput.value = 'none'; // 可以根據需要設定值

        const noAddOnLabel = document.createElement('label');
        noAddOnLabel.setAttribute('for', 'no_add_ons');
        noAddOnLabel.textContent = '無加料';

        // 將「無加料」選項添加到容器中
        addOnsContainer.appendChild(noAddOnInput);
        addOnsContainer.appendChild(noAddOnLabel);

        // 根據每個條件生成對應的選項
        addOnsOptions.forEach(option => {
            if (option.condition) {
                // 動態創建 input 和 label
                const input = document.createElement('input');
                input.type = 'radio';
                input.id = option.id;
                input.name = 'materials-options';
                input.value = option.value;

                const label = document.createElement('label');
                label.setAttribute('for', option.id);
                label.textContent = option.label;

                // 將 input 和 label 添加到容器中
                addOnsContainer.appendChild(input);
                addOnsContainer.appendChild(label);
            }
        });
    }

    //F09 更新localstorage
    //renderproductdetail->handlelightbox->updateLocalStorage
    function updateLocalStorage() {
        //先抓目前頁面上所有商品項
        const cartItems = document.querySelectorAll(".cart-item");
        //若無商品了就跳提示並回上一頁
        if (cartItems.length === 0) {
            Swal.fire({
                title: '購物車為空',
                text: '沒有商品，將返回上一頁',
                icon: 'info',
                confirmButtonText: '確定'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.history.back();
                }
            });
        }
        const typeOneData = {};
        //每個抓到商品ID，甜度冰塊尺寸杯數等資訊存到陣列中
        cartItems.forEach(product => {
            const productDetail = product.closest(".item-content").querySelector(".product-detail");
            const productId = product.dataset.productId;
            const buynum = productDetail.querySelector('[data-type="buyNum"]').textContent.match(/\d+/)[0];
            parseInt(buynum)
            const itemdata ={
                temperature:productDetail.querySelector('[data-type="temperature"]').textContent.trim().replace(/ \/$/, ''),
                sweetness:productDetail.querySelector('[data-type="sweetness"]').textContent.trim().replace(/ \/$/, ''),
                toppings:productDetail.querySelector('[data-type="toppings"]').textContent.trim().replace(/ \/$/, ''),
                size:productDetail.querySelector('[data-type="size"]').textContent.trim().replace(/ \/$/, ''),
                buyNum:buynum
            }
            typeOneData[productId] = itemdata;
        });
        //覆蓋現在localstorage資料
        localStorage.setItem('cart', JSON.stringify(typeOneData));
    }

    //F14 獲得的優惠券動態生成選項
    function getcoupons(customerId){
        fetch(`/cart/getCoupon/${customerId}`)
            .then(response=>{
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                return response.json();
            })
            .then(coupons => {
                console.log("coupons : ",coupons)
                let options = couponSelect_el.querySelectorAll('option:not([disabled])');
                options.forEach(option => option.remove());
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
    //F15 獲得會員卡餘額及相關事件綁定
    function GetMemberCardPoint(storeId,customerId){
        fetch(`/cart/checkoutlist/getMemberCard/${storeId}/${customerId}`)
            .then(response=>{
                if (!response.ok) {
                    return response.json().then(errorData => {
                        throw new Error(errorData.message);
                    });
                }
                return response.json();
            })
            .then(data=>{
                console.log("membercard:",data)
                console.log("會員卡積分:", data.points);
                if(data.points<10){
                    membercard_count_el.textContent=`會員點數為${data.points} 點，會員點數不足`;
                    membercard_minus_number_el.textContent = "$0";
                }else if(data.points>=10){
                    membercard_count_el.textContent=`會員點數為${data.points} 點，10點可折抵50元`;
                    membercard_minus_number_el.textContent = "$50";
                    loyaltyCardId = data.loyaltyCardId;
                }else{
                    membercard_count_el.textContent="無會員卡資料";
                    membercard_minus_number_el.textContent = "$0";
                }
                Calculatetotal();
            })
            .catch(error=>{
                console.error('Error loading coupons:', error);
            })
    }
    //F20 計算訂單金額
    function Calculatetotal() {
        totalAmount = 0;
        totalquanity = 0;

        // 抓所有的商品明細項目，根據 data-type 屬性來取得價格和數量
        document.querySelectorAll('.item-content')
            .forEach(itemContent => {
                let priceEl = itemContent.querySelector('div[data-type="price"]');
                let quantityEl = itemContent.querySelector('div[data-type="buyNum"]');
                let priceText = priceEl.textContent.trim();
                let quantityText = quantityEl.textContent.trim();
                let price = parseFloat(priceText.replace('$', '').replace('/', ''));
                let quantity = parseInt(quantityText.replace(' 杯', ''));

                totalquanity += quantity;
                totalAmount += price * quantity;
                productamount = totalAmount; // 保存商品總額
            });

        // 顯示商品總數與折扣前總價
        product_unit_el.textContent = `商品X${totalquanity}`;
        product_amount_el.textContent = `$${totalAmount}`;

        // 加上平台費用
        PlatformfeeText = platform_fee_number_el.textContent.trim();
        PlatformAmount = parseFloat(PlatformfeeText.replace('$', ''));
        totalAmount += PlatformAmount;

        // 優惠券折抵
        CouponAmountText = coupon_minus_number_el.textContent.trim();
        CouponAmount = parseFloat(CouponAmountText.replace('$', ''));
        totalAmount -= CouponAmount;
       

        // 會員卡折抵
        MemberCardAmountText = membercard_minus_number_el.textContent.trim();
        MemberCardAmount = parseFloat(MemberCardAmountText.replace('$', ''));
        totalAmount -= MemberCardAmount;

        // 檢查最低金額 30 元（平台費用），折扣後最低應為 30
        if (totalAmount < 30) {
            totalAmount = 30;
        }

        // 會員錢包折抵部分邏輯
        MoneyBagAmountText = moneybag_discount_number_el.value.trim(); // 抓取使用者輸入的金額
        MoneyBagAmount = parseInt(MoneyBagAmountText.replace('$', ''));

        if (!isNaN(MoneyBagAmount) && MoneyBagAmount > 0) {
            totalAmount -= MoneyBagAmount;

            // 如果錢包折抵後的總金額低於 30 元，調整錢包折抵金額
            if (totalAmount < 30) {
                let adjustAmount = 30 - totalAmount; // 多扣的金額
                MoneyBagAmount -= adjustAmount; // 調整後的錢包折抵額
                totalAmount = 30; // 確保總金額不低於 30 元
                // 更新錢包折抵輸入框和顯示金額
                moneybag_discount_number_el.value = MoneyBagAmount;
                moneybag_minus_number_el.textContent = `$${MoneyBagAmount}`;
            } else {
                moneybag_minus_number_el.textContent = `$${MoneyBagAmount}`;
            }
        }
        // 顯示總金額
        last_totalAmount = totalAmount;
        total_amount_el.textContent = `$${totalAmount}`;
    }

})


