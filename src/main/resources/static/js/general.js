function nullToEmpty(data) {
    return (data == null) ? "" : data;
}

document.addEventListener("DOMContentLoaded", function () {
    fetch('header.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector(".header").innerHTML = data;
        });
    fetch('footer.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector(".footer").innerHTML = data;
        });

});
document.addEventListener("click", function (e) {
    if (e.target.classList.contains("logOut")) {
        Swal.fire({
            title: "確認登出?",
            showDenyButton: true,
            confirmButtonText: "登出",
            DenyButtonText: "否"
        }).then((result) => {
            if (result.isConfirmed) {
                fetch("member/logout")
                    .then(res => res.text())
                    .then(data => {
                        console.log(data);
                        sessionStorage.removeItem("memberData");
                        Swal.fire({
                            icon: "success",
                            title: "已登出",
                            showConfirmButton: false,
                            timer: 1000
                        }).then(() => {
                            location.replace("../login.html");
                        })
                    })

            }
        });
    }
    console.log(e.target);

    if(e.target.classList.contains("queryMember")){
        // ASIDE
        const queryBarUseImg = document.querySelector("#queryBarUseImg");
        const queryBarUseName = document.querySelector("#queryBarUseName");

        console.log(sessionStorage.getItem("memberData"));
        fetch(`member`, {
            method: "POST",
        }).then(res => res.json())
            .then(data => {
                console.log(data)
                if (data.successful) {
                    let userImg = (nullToEmpty(data.customerImg) === "") ? "" : `data:image/png;base64,${data.customerImg}`;
                    queryBarUseImg.setAttribute("src", userImg);
                    queryBarUseName.innerText = nullToEmpty(data.nickname);
                }
            });
    }
})