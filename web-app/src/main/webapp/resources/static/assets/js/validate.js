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
    var modelName = document.getElementById('modelName').value;
    var releaseDate = document.getElementById('releaseDate').value;

    if(modelName=="" || releaseDate=="") {
        alert('Please fill all fields');
        return false;
    }
    else {
        return true;
    }
}