
 document.addEventListener("DOMContentLoaded", function () {
    let today = new Date().toISOString().split("T")[0];
    let checkInInput = document.getElementById("checkinDate");
    let checkOutInput = document.getElementById("checkoutDate");

    checkInInput.setAttribute("min", today);

    checkInInput.addEventListener("change", function () {
    if (this.value) {
    let checkInDate = new Date(this.value);
    let minCheckOutDate = new Date(checkInDate);
    minCheckOutDate.setDate(minCheckOutDate.getDate() + 1);
    checkOutInput.removeAttribute("disabled");
    checkOutInput.setAttribute("min", minCheckOutDate.toISOString().split("T")[0]);
}
    else {
    checkOutInput.setAttribute("disabled", "true");
    checkOutInput.value= "";
}
});
});

