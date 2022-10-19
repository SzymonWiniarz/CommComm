function todayAsString() {
    const today = new Date();

    var month = today.getMonth() + 1;
    var day = today.getDate();
    var year = today.getFullYear();

    if (month < 10) {
        month = "0" + month.toString();
    }

    if (day < 10) {
        day = "0" + day.toString();
    }

    return year + "-" + month + "-" + day;
}

export { 
    todayAsString 
}