// 1보다 큰 숫자로 구성되어있는지 검사
function isDigitNumber(num) {
    return /^\d+$/.test(num);
}

// 가격 포맷에 맞는지 검사
function isPriceFormat(num) {
    return /^[1-9]\d*0$/.test(num);
}

// 빈 값인지 검사
function isNull(input) {
    return input === null || input === "";
}