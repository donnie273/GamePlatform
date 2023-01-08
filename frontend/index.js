//login
function login(){
    const login_username = $(".login_username").val();
    const login_password = $(".login_password").val();
    const url = 'http://localhost:3000/api/v1/userlogin';

    if(login_username === '' || login_password === ''){
        $(".login_error_no_credential").show();
        return;
    }

    data = {
        "username": login_username,
        "password": login_password
    }

    $.ajax({url: url, method: 'POST', data: data,
    success: function(result){
        const appUserId = result.userId;
        sessionStorage.setItem('id', appUserId);
        setTimeout(function(){ location.href="home.html"; }, 1000);
        console.log(result);
    },
    error: function(xhr, status, err){
        $(".login_error_no_credential").hide();
        $(".login_error_incorrect_credential").show();
        console.log(xhr + ' ' + status + '' + err);
    }})
}

//logout
function logout(){
    sessionStorage.removeItem('id');
    setTimeout(function(){ location.href="login.html"; }, 1000);
}

//topup
$(".topup").click(function(){
    $(".topup-area").show();
})

$(".topupconfirm").click(function(){
    const id = getUserId();
    const amount = $(".topup-amount").val();
    if(amount === ''){
        $(".topup-error").show();
        return;
    }
    const url = 'http://localhost:3000/api/v1/topup?userId=' + id + '&amount=' + amount;
    $.ajax({url: url, method: 'GET',
    success: function(result){
        setTimeout(function(){ location.href="topup.html?amount=" + amount
            + '&balanceBefore=' + result.balanceBefore + '&balanceAfter=' + result.balanceAfter; }, 1000);
    },
    error: function(xhr, status, err){
        console.log(xhr + ' ' + status + '' + err);
    }})
})

$(".topupcancel").click(function(){
    $(".topup-area").hide();
    $(".topup-error").hide();
})

$(".redirecttomywallet").click(function(){
    location.href="mywallet.html"
})

//change password
$(".chgpwd").click(function(){
    $(".chgpwd-area").show();
})

$(".savepwd").click(function(){
    const id = getUserId();
    const url = 'http://localhost:3000/api/v1/updatePassword?userId=' + id;
    const data = {
        "password": $(".conpw").val()
    };

    $.ajax({url: url, method: 'POST', data: data,
    success: function(result){
        $(".chgpwd-area").hide();
        alert("Password reset successfully");
    },
    error: function(xhr, status, err){
        console.log(xhr + ' ' + status + '' + err);
    }})
})

$(".conpw").blur(function(){
    const oldpw = $(".oldpw").val();
    const newpw = $(".newpw").val();
    const conpw = $(".conpw").val();

    const id = getUserId();
    const url = 'http://localhost:3000/api/v1/appuser/' + id;
    $.ajax({url: url, method: 'GET',
    success: function(result){
        if(oldpw !== result.password){
            $(".oldpwerror").show();
        }else{
            $(".oldpwerror").hide();
        }
    },
    error: function(xhr, status, err){
        console.log(xhr + ' ' + status + '' + err);
    }})

    if(newpw !== conpw || oldpw === '' || newpw === '' || conpw === ''){
        $(".pwerror").show();
        $(".savepwd").attr("disabled", true);
    }else{
        $(".pwerror").hide();
        $(".savepwd").attr("disabled", false);
    }
})

$(".cancelpwd").click(function(){
    $(".chgpwd-area").hide();
    $(".pwerror").hide();
    $(".oldpwerror").hide();
})

//purchase
function purchase(pid){
    const id = getUserId();
    const url = 'http://localhost:3000/api/v1/item/' + pid + '?userId=' + id;
    $.ajax({url: url, method: 'GET',
    success: function(result){
        setTimeout(function(){ location.href="mallpurchase.html?cost=" + result.cost + '&pid=' + pid; }, 1000);
    },
    error: function(xhr, status, err){
        console.log(xhr + ' ' + status + '' + err);
    }})
}

$(".purchase-item").click(function(){
    const id = sessionStorage.getItem('id');
    const pid = location.search.split("&")[1].split("=")[1];
    const url = 'http://localhost:3000/api/v1/purchase/' + pid + '?userId=' + id;
    $.ajax({url: url, method: 'GET',
    success: function(result){
        alert('Purchase successfully');
        setTimeout(function(){ location.href="mall.html"; }, 1000);
    },
    error: function(xhr, status, err){
        console.log(xhr + ' ' + status + '' + err);
    }})
})

$(".redirecttomall").click(function(){
    location.href="mall.html";
})

//subscribe
function subscribe(sid){
    setTimeout(function(){ location.href= 'plansubscribe.html?id=' + sid; }, 1000);
}

$(".subscribe-plan").click(function(){
    const id = getUserId();
    const sid = location.search.split('=')[1];
    const url = 'http://localhost:3000/api/v1/subscribe/' + sid + '?userId=' + id;
    $.ajax({url: url, method: 'GET',
    success: function(result){
        alert('subsribe successfully');
        setTimeout(function(){ location.href="home.html"; }, 1000);
    },
    error: function(xhr, status, err){
        console.log(xhr + ' ' + status + '' + err);
    }})
})

$(".redirecttosubscription").click(function(){
    location.href="subsciption.html"
})

//transaction history
const base64ToArrayBuffer = (base64) => {
    const binaryString = window.atob(base64);
    const binaryLen = binaryString.length;
    const bytes = new Uint8Array(binaryLen);
    for (let i = 0; i < binaryLen; i++) {
      const ascii = binaryString.charCodeAt(i);
      bytes[i] = ascii;
    }
    return bytes;
  };
  
  const saveByteArray = (fileName, byte) => {
    const blob = new Blob([byte], { type: "application/pdf" });
    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    link.download = fileName;
    link.click();
  };

$(".search-history").click(function(){
    const m1 = $(".m1").val();
    const y1 = $(".y1").val();
    const m2 = $(".m2").val();
    const y2 = $(".y2").val();
    const fromDate = '01/' + m1 + '/' + y1;
    const toDate = '31/' + m2 + '/' + y2;

    const id = getUserId();
    const url = 'http://localhost:3000/api/v1/history?userId=' + id + '&fromDate=' + fromDate +'&toDate=' + toDate;

    console.log('userId=' + id + '&fromDate=' + fromDate +'&toDate=' + toDate);

    if(y1 > y2 || ((y1 === y2) && m1 > m2)){
        $(".dateerror").text('From Date must before To Date');
        $(".history").hide();
    }else{
        $(".dateerror").text('');
        
        $.ajax({url: url, method: 'GET',
        success: function(result){
            const transactionHistory = result.transactionHistory;
            const history = transactionHistory.map(th => '<tr><td>' + th.transactionId+ '</td><td>' + th.transactionDate.split("T")[0] + '</td><td>' + th.purchaseAmount
                + '</td><td>' + th.discountedAmount + '</td></tr>');
            const total = '<tr><td><b>Total</b></td><td></td><td>' + result.totalPurchaseAmount
            + '</td><td>' + result.totalDiscountedAmount + '</td></tr>';
            
            $(".transactionhistory").html(history + total);
            $(".download").show();
            console.log(result);
        },
        error: function(xhr, status, err){
            console.log(xhr + ' ' + status + '' + err);
        }})
        $(".history").show();
    }
})

$(".downloadHistory").click(function(){
    const m1 = $(".m1").val();
    const y1 = $(".y1").val();
    const m2 = $(".m2").val();
    const y2 = $(".y2").val();
    const fromDate = '01/' + m1 + '/' + y1;
    const toDate = '31/' + m2 + '/' + y2;

    const id = getUserId();
    const url = 'http://localhost:3000/api/v1/history?userId=' + id + '&fromDate=' + fromDate +'&toDate=' + toDate;

        $.ajax({url: url, method: 'GET',
        success: function(result){
            saveByteArray('TransactionHistory.txt',base64ToArrayBuffer(result.download));
            console.log(download);
        },
        error: function(xhr, status, err){
            console.log(xhr + ' ' + status + '' + err);
        }})
})

//registerUser
function registerUser(){
    const username = $("#username").val();
    const fullname = $("#fullname").val();
    const password = $("#password").val();
    const inputPassword = $("#inputPassword").val();

    if(username === '' || fullname === '' || password === '' || inputPassword === ''){
        $(".register_error_no_credential").show();
        return;
    }else{
        $(".register_error_no_credential").hide();
    }

    if(password !== inputPassword){
        $(".register_error_incorrect_credential").show();
        return;
    }else{
        $(".register_error_incorrect_credential").hide();
    }

    const data = {
        "username": username,
        "fullname": password,
        "password": fullname
    }
    
    $.ajax({url: 'http://localhost:3000/api/v1/appuser/save', data: data, method: 'POST',
    success: function(result){
        $(".myUsername").text(result.username);
        $(".myFullname").text(result.fullname);
        $(".myBalance").text(result.balance);
        alert("Register successfully");
        setTimeout(function(){ location.href="login.html"; }, 1000);
    },
    error: function(xhr, status, err){
        console.log(xhr + ' ' + status + '' + err);
    }})
}

function getUserId(){
    return sessionStorage.getItem('id');
}






