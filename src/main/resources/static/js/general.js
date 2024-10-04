function nullToEmpty(data) {
    return (data == null) ? "" : data;
}

document.addEventListener("DOMContentLoaded", function () {
    fetch('header.html')
        .then(response => response.text())
        .then(data => {
            document.querySelector(".header").innerHTML = data;
            const isLoginUl = document.querySelector("#isLogin");
            const noLoginUl = document.querySelector("#noLogin");
            fetch("member/login")
                .then(response => response.text())
                .then(data => {
                    if (data === "true") {
                        noLoginUl.classList.add("hiddenMenu");
                        isLoginUl.classList.remove("hiddenMenu");
                    } else {
                        noLoginUl.classList.remove("hiddenMenu");
                        isLoginUl.classList.add("hiddenMenu");
                    }
                })
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

    if (e.target.classList.contains("queryMember")) {
        // ASIDE
        const queryBarUseImg = document.querySelector("#queryBarUseImg");
        const queryBarUseName = document.querySelector("#queryBarUseName");
        fetch(`member`, {
            method: "POST",
        }).then(res => res.json())
            .then(data => {
                if (data.successful) {
                    let userImg = (nullToEmpty(data.customerImg) === "") ? "" : `data:image/png;base64,${data.customerImg}`;
                    queryBarUseImg.setAttribute("src", userImg);
                    queryBarUseName.innerText = nullToEmpty(data.nickname);
                }
            });
    }
})