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

function getDayOfWeekFromDate(date) {
    const dayOfWeekIndex = date.getDay();
    switch (dayOfWeekIndex) {
        case 0:
            return "Niedziela";
        case 1:
            return "Poniedziałek"
        case 2:
            return "Wtorek"
        case 3:
            return "Środa"
        case 4:
            return "Czwartek"
        case 5:
            return "Piątek"
        case 6:
            return "Sobota";
        default:
            return "Dzień Świstaka";
    }
}

function isWeekend(date) {
    return date.getDay() == 0 || date.getDay() == 6;
}

function dayFriendlyLabel(date) {
    var month = date.getMonth() + 1;
    var day = date.getDate();

    if (month < 10) {
        month = "0" + month.toString();
    }

    if (day < 10) {
        day = "0" + day.toString();
    }

    const dayOfWeek = getDayOfWeekFromDate(date);

    return day + "." + month + " " + dayOfWeek;
}

function getNearestDays(numberOfDays) {
    const days = Array();
    var date = new Date();
    var dayId = 1;

    while (days.length < numberOfDays) {
      if (!isWeekend(date)) {
        const dayLabel = dayFriendlyLabel(date);
        days.push({
          id: dayId++,
          date: date,
          label: dayLabel,
        });
      }

      date.setDate(date.getDate() + 1);
    }

    return days;
  }

export {
    todayAsString,
    isWeekend,
    dayFriendlyLabel,
    getNearestDays
}