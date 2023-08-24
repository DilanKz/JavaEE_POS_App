let cusId;

//customer fields
const cusIDF = $('#cid');
const cusNameF = $('#Name');
const cusAddressF = $('#Address');
const cusContactF = $('#contact');

//buttons
const btnAdd = $("#btnAdd");
const tblCustomers = $("#tblCustomers");
const btnUpdate = $("#btnUpdate");
const btnDelete = $("#btnDelete");
const btnClear = $("#btnClear");




//id increment
function incrementCusId(currentID) {
    if (currentID==='no'){
        cusId='C00-001';
        cusIDF.val(cusId);
    }else {
        let number =parseInt(currentID.slice(4), 10);
        number++;
        cusId = "C00-" + number.toString().padStart(3, "0");
        console.log(cusId);
        cusIDF.val(cusId);
    }
}
incrementCusId('no');

function clearCustomerFields() {
    //cusIDF.val("");
    cusNameF.val("");
    cusNameF.focus();
    cusAddressF.val("");
    cusContactF.val("");

}

function addCustomersToTable() {
    //get All customers
}

btnAdd.click(function (){
    const customerForm = $("#customerForm");

    //Adding a customer
    //Ajax
    /*customerList.push( new Customer(cusID,cusName,cusAddress,cusContact));
    addCustomersToTable()

    clearCustomerFields();
    incrementCusId(cusID);*/
});

tblCustomers.dblclick(function (event){

    btnUpdate.prop('disabled',false);
    btnDelete.prop('disabled',false);
    btnAdd.prop('disabled',true);

    let selectedCustomerRow = event.target.closest("tr");

    cusIDF.val(selectedCustomerRow.cells[0].textContent);
    cusNameF.val(selectedCustomerRow.cells[1].textContent);
    cusAddressF.val(selectedCustomerRow.cells[2].textContent);
    cusContactF.val(selectedCustomerRow.cells[3].textContent);

});


btnUpdate.click(function (){

    //Update Customers
    if (confirm("Are you sure you want to Update this Customer?")) {
        let cusID = cusIDF.val();
        let cusName = cusNameF.val();
        let cusAddress = cusAddressF.val();
        let cusContact = cusContactF.val();

        //Ajax

        /*clearCustomerFields();

        console.log(customerList);

        btnUpdate.prop('disabled',true);
        btnDelete.prop('disabled',true);
        btnAdd.prop('disabled',false);

        //getting the first td of the last tr and
        incrementCusId(lastTr.find('td:first').text());
        cusIDF.val(cusId);*/
    }
});

btnDelete.click(function (){
    //Deleting a Customer
    if (confirm("Are you sure you want to delete this Customer?")) {
        let cusID = cusIDF.val();

        //Ajax
        /*clearCustomerFields();

        btnUpdate.prop('disabled',true);
        btnDelete.prop('disabled',true);
        btnAdd.prop('disabled',false);

        incrementCusId(lastTr.find('td:first').text());
        cusIDF.val(cusId);*/
    }
});

btnClear.click(function (){
    clearCustomerFields();
});

//Regex Validations
$('#cid, #Name, #Address, #contact').keyup(function (event){
    console.log(event.key)

    if (event.key ==='Tab'){
        event.preventDefault();
    }
});

cusNameF.keyup(function (event){


    if (/^[A-Za-z]+$/.test(cusNameF.val())){

        cusNameF.css('border-color', '#dee2e6');

        if (event.key ==='Enter'){
            cusAddressF.focus();
        }

    }else {
        cusNameF.css('border-color', 'red');
    }

});

cusAddressF.keyup(function (event){
    if (/^[A-Za-z\s.'-]+$/.test(cusAddressF.val())){
        cusAddressF.css('border-color', '#dee2e6');
        if (event.key ==='Enter'){

            cusContactF.focus();
        }
    }else {
        cusAddressF.css('border-color', 'red');
    }

});

cusContactF.keyup(function (event){

    if (/^(?:\+94|0)(?:\d{9}|\d{2}-\d{7})$/.test(cusContactF.val())){
        cusContactF.css('border-color', '#dee2e6');

        if (event.key ==='Enter'){
            addCustomer();
        }
    }else {
        cusContactF.css('border-color', 'red');
    }
});

function validateFields(){
    if (!/^[A-Za-z]+$/.test(cusNameF.val())){
        cusNameF.focus();
        cusNameF.css('border-color', 'red');
        return false;
    }
    if (!/^[A-Za-z\s.'-]+$/.test(cusAddressF.val())){
        cusAddressF.focus();
        cusAddressF.css('border-color', 'red');
        return false;
    }
    if (!/^(?:\+94|0)(?:\d{9}|\d{2}-\d{7})$/.test(cusContactF.val())){
        cusContactF.focus();
        cusContactF.css('border-color', 'red');
        return false;
    }

    cusNameF.css('border-color', '#dee2e6');
    cusAddressF.css('border-color', '#dee2e6');
    cusContactF.css('border-color', '#dee2e6');
    return true;
}
