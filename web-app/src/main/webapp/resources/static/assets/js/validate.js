function checkType() {
    var typeName = document.getElementById('typeName').value;

    if(typeName=="") {
        alert('Please fill all fields');
        return false;
    }
    else {
        return true;
    }
}

function checkModel() {
    var pattern = /^([0-9]{4})-([0-9]{2})-([0-9]{2})$/;
    var modelName = document.getElementById('modelName').value;
    var releaseDate = document.getElementById('releaseDate').value;

    if(modelName=="" || releaseDate=="") {
        alert('Please fill all fields');
        return false;
    } else if(!releaseDate.match(pattern)) {
            alert('Please use correct date format "yyyy-MM-dd"');
            return false;
    } else return true;
}

function checkDates() {
    var pattern = /^([0-9]{4})-([0-9]{2})-([0-9]{2})$/;
    var begin = document.getElementById('begin').value;
    var end = document.getElementById('end').value;

    if((begin != "" && !begin.match(pattern)) || (end != "" && !end.match(pattern))) {
        alert('Please use correct date format "yyyy-MM-dd"');
        return false;
    } else return true;
}

function checkDeletingType(quantity) {
    if(quantity > 0) {
        alert('You can\'t delete type with models');
        return false;
    } else return true;
}
