function vatCheck(vat) {
    let tmp = "12121241";
    let sum = 0;
    let pattern = /^\d{8}$/;
    if (!pattern.test(vat)) {
        return false
    }
    for (i = 0; i < 8; i++) {
        let s1 = parseInt(vat.substr(i, 1));
        let s2 = parseInt(tmp.substr(i, 1));
        sum += cal(s1 * s2);
    }
    if (!valid(sum)) {
        if (vat.substr(6, 1) === "7") return (valid(sum + 1));
    }
    return (valid(sum));
}

function valid(n) {
    return (n % 10 === 0);
}

function cal(n) {
    let sum = 0;
    while (n !== 0) {
        sum += (n % 10);
        n = (n - (n % 10)) / 10;  // 取整數
    }
    return sum;
}


function phoneCheck(phone) {
    let pattern = /(\d{2,3}-?|\(\d{2,3}\))\d{3,4}-?\d{4}|09\d{2}(\d{6}|-\d{3}-\d{3})/;
    return pattern.test(phone)
}