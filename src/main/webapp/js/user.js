function getCurUser() {
    return JSON.parse(localStorage.getItem("curUser"));
}

function saveCurUser(user) {
    localStorage.setItem("curUser", JSON.stringify(user)) ;
    return user ;
}