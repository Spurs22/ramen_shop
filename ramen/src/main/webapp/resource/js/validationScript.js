function isDigitNumber(num) {
    return /^\d+$/.test(num);
}

function isPriceFormat(num) {
    return /^[1-9]\d*0$/.test(num);
}

function isNull(input) {
    if (input === null || input === "") {
        return true;
    }
    return false;
}