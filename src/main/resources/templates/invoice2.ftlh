<!DOCTYPE html>

<html lang="ar">
  <head>
    <meta charset="utf-8">
    <title>${invoice.id}</title>
    
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <meta name="description" content="Invoicebus Invoice Template">
    <meta name="author" content="Invoicebus">

    <meta name="template-hash" content="baadb45704803c2d1a1ac3e569b757d5">

    <style>

/* Font-Face */
@font-face {
	font-family: "AlinmaTheSans-Bold";
	font-weight: normal;
	font-style: normal;
}

@font-face {
	font-family: "AlinmaTheSans-Plain";
	font-weight: normal;
	font-style: normal;
}

body, html {
 line-height: 1;
	font-family: AlinmaTheSans-Plain, "Trebuchet MS", Helvetica, sans-serif;
	direction: rtl;
}

* {
	font-family: AlinmaTheSans-Plain, "Trebuchet MS", Helvetica,
		sans-serif !important;
}

ol, ul {
  list-style: none;
}

table {
  border-collapse: collapse;
  border-spacing: 0;
}

caption, th, td {
  text-align: right;
  font-weight: normal;
  vertical-align: middle;
}

q, blockquote {
  quotes: none;
}
q:before, q:after, blockquote:before, blockquote:after {
  content: "";
  content: none;
}

a img {
  border: none;
}

article, aside, details, figcaption, figure, header, hgroup, main, menu, nav, section, summary {
  display: block;
}

.clearfix {
  display: block;
  clear: both;
}

.hidden {
  display: none;
}

b, strong, .bold {
  font-weight: bold;
}

#container {
  font: normal 15px/1.4em;
  margin : 0px 0px 0px;
  min-height: 100%;
  background: #F7EDEB;
  background-size: 100%;
  color: #5B6165;
  position: relative;
}

#memo {
  padding-top: 50px;
  margin: 0px 30px 0 10px;
  border-bottom: 1px solid #ddd;
  height: 115px;
}
#memo .logo {
  float: left;
  margin-right: 20px;
}
#memo .logo img {
  width: 100px;
  height: 100px;
}
#memo .company-info {
  float: right;
  text-align: right;
}
#memo .company-info > div:first-child {
  line-height: 1em;
  font-weight: bold;
  font-size: 22px;
  color: #B32C39;
}
#memo .company-info span {
  font-size: 11px;
  display: inline-block;
  min-width: 20px;
}
#memo:after {
  content: '';
  display: block;
  clear: both;
}

#invoice-title-number {
  font-weight: bold;
  margin: 10px 30px 0 20px;
}
#invoice-title-number span {
  line-height: 0.88em;
  display: inline-block;
  min-height: 21px;
}
#invoice-title-number #title {
  padding: 0px 2px 0px 60px;
  font-size: 16px;
  background: #F4846F;
  color: white;
}
#invoice-title-number #number {
  margin-left: 10px;
  font-size: 14px;
  position: relative;
  top: -5px;
}

#client-info {
  float: right;
  margin: 0px 30px 0 3px;
  min-width: 220px;
}
#client-info > div {
  min-width: 20px;
}
#client-info span {
  display: block;
  min-width: 20px;
}
#client-info > span {
  text-transform: uppercase;
}

#invoice-info {
	float: left;
	margin: 10px 20px 0 10px;
}

#invoice-info>div {
	float: left;
}

#invoice-info>div>span {
	display: block;
	min-width: 20px;
	min-height: 18px;
	margin-bottom: 3px;
}

#invoice-info>div:last-child {
	margin-left: 20px;
}

#invoice-info:after {
	content: '';
	display: block;
	clear: both;
}


table {
  table-layout: fixed;
}
table th, table td {
  vertical-align: top;
  word-break: keep-all;
  word-wrap: break-word;
}

#items {
  margin: 35px 30px 0 30px;
}

#items table {
  border-collapse: separate;
  width: 100%;
}
#items table th {
  font-weight: bold;
  padding: 5px 8px;
  text-align: right;
  background: #B32C39;
  color: white;
  text-transform: uppercase;
}


#items table td {
  padding: 9px 8px;
  text-align: right;
  border-bottom: 1px solid #ddd;
}


#sums {
  float:left;
  margin: 50px 0 75px 0;
  background: url("https://res.cloudinary.com/genhub/image/upload/v1629716996/total-stripe-firebrick_ebqixc.png") right bottom no-repeat;
}
#sums table {
  float:left;
}
#sums table tr th, #sums table tr td {
  min-width: 100px;
  padding: 9px 8px;
  text-align: right;
}
#sums table tr th {
  font-weight: bold;
  text-align: right;
  padding-left: 35px;
}
#sums table tr td.last {
  min-width: 0 !important;
  max-width: 0 !important;
  width: 0 !important;
  padding: 0 !important;
  border: none !important;
  padding-left: 30px;
  margin-left:30px;
}
#sums table tr.amount-total th {
  text-transform: uppercase;
  
}
#sums table tr.amount-total th, #sums table tr.amount-total td {
  font-size: 15px;
  font-weight: bold;
}
#sums table tr:last-child th {
  text-transform: uppercase;
}
#sums table tr:last-child th, #sums table tr:last-child td {
  font-size: 15px;
  font-weight: bold;
  color: white;
}


#terms {
  float: right;
  margin-top: 50px;
}
#terms .notes {
  margin: 0px 30px 0 10px;
  min-height: 30px;
  min-width: 50px;
  color: #B32C39;
}
#terms .payment-info div {
  margin-bottom: 3px;
  min-width: 20px;
}

.thank-you {
  margin: 10px 0 30px 0;
  display: inline-block;
  min-width: 20px;
  text-transform: uppercase;
  font-weight: bold;
  line-height: 0.88em;
  float: right;
  padding: 0px 30px 0px 2px;
  font-size: 50px;
  background: #F4846F;
  color: white;
}

.ib_bottom_row_commands {
  margin-left: 30px !important;
}

footer{
  width: 100%;
  text-align: center;
 }
 
.footer-basic {
  width: 100%;
  text-align: center;
  padding:5px 0;
  background-color:#ffffff;
  color:#4b4c4d;
}

.footer-basic ul {
  padding:0;
  list-style:none;
  text-align:center;
  font-size:18px;
  line-height:1.6;
  margin-bottom:0;
}

.footer-basic li {
  padding:0 10px;
}

.footer-basic ul a {
  color:inherit;
  text-decoration:none;
  opacity:0.8;
}

.footer-basic ul a:hover {
  opacity:1;
}

.footer-basic .social {
  text-align:center;
  padding-bottom:5px;
}

.footer-basic .social > a {
  font-size:8px;
  width:30px;
  height:30px;
  line-height:30px;
  text-decoration: none;
  display:inline-block;
  text-align:center;
  border-radius:50%;
  border:1px solid #ccc;
  margin:0 8px;
  color:inherit;
  opacity:0.75;
}

.footer-basic .copyright {
  margin-top:15px;
  text-align:center;
  font-size:13px;
  color:#aaa;
  margin-bottom:0;
}


@media print {
	#container {
		overflow: hidden !important;
	}
	#container footer {
		position: fixed;
		bottom: 10px;
	}
}
    </style>
    
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  </head>
  <body>
    <div id="container">
      <section id="memo">
        <div class="logo">
          <img src='${currentUser.companyLogo?trim}' />
        </div>
        
        <div class="company-info">
          <div>${currentUser.companyName?trim}</div>

          <br />
          
          <span>${currentUser.address?trim}</span>
        
          <br />
          
          <span>${currentUser.phone?trim}</span>
          <span>${currentUser.email?trim}</span>
        </div>

      </section>

      <section id="invoice-title-number">
      
        <span id="title">فاتورة رقم </span>
        <span id="number">${invoice.id}</span>
        
      </section>
      
      <div class="clearfix"></div>
      
      <section id="client-info">
        <span>فاتورة إلى</span>
        <div>
          <span class="bold">${invoice.customer.name?trim}</span>
        </div>
        
        <div>
          <span>${invoice.customer.address?trim}</span>
        </div>
        
        <div>
          <span>${invoice.customer.state?trim}</span>
        </div>
        
        <#if invoice.customer.email?has_content>
			<div>
				<span><a href="mailto:${invoice.customer.email?trim}">${invoice.customer.email?trim}</a></span>
			</div>
		</#if>
        
      
      </section>
      
      
      	<section id="invoice-info">
			<div>
				<span>${invoice.createdAt?string('dd-MM-yyyy')}</span> <span>--------------</span>
			</div>

			<div>
				<span>تاريخ الفاتورة</span> <span>تاريخ الدفع</span>
			</div>
		</section>
      
      <div class="clearfix"></div>
      
      <section id="items">
        
        <table cellpadding="0" cellspacing="0">
        
         <tr>
				<th>المجموع</th>
					<th>الخصم</th>
					<th>الكمية</th>
					<th>السعر</th>
					<th width="180">المنتج</th>
					<th width="10">#</th>
				</tr>

				<#list invoice.items as item>
				<tr>
					<td>${itemTotalAfterDiscount(item)}</td>
					<td>${item.discount}</td>
					<td>${item.quantity}</td>
					<td>${item.product.price}</td>
					<td width="180">
						${item.product.name}
					</td>
					<td width="10">${item?index + 1}</td>
				</tr>
				</#list>
          
        </table>
        
      </section>
      
      <section id="sums">
      
        <table cellpadding="0" cellspacing="0">
        
        <tr>
					<td>${itemSubtotal(invoice)}</td>
					<th>المجموع الفرعي</th>
				</tr>

				<tr data-hide-on-quote="true">
					<td></td>
					<th>ضريبة</th>
				</tr>

				<tr class="amount-total">
					<td>${itemSubtotal(invoice)}</td>
					<th>المجموع الإجمالي</th>
				</tr>
          
        </table>

        <div class="clearfix"></div>
        
      </section>
      
     
  <div class="clearfix"></div>
  
      <section id="invoice-info">

        <br />

        <div>
          <span class="bold">التوقيع والإعتماد</span> 
          <span>---------------------</span>
        </div>
      </section>
      
      <section id="terms">

        <div class="notes">
				<ul>
					<li>إجمالي الدفعة المستحقة خلال 30 يومًا</li>
					<li>يرجى تضمين رقم الفاتورة على الشيك الخاص بك</li>
				</ul>
		</div>

        <br />

<!--
        <div class="payment-info">
          <div>{payment_info1}</div>
          <div>{payment_info2}</div>
          <div>{payment_info3}</div>
          <div>{payment_info4}</div>
          <div>{payment_info5}</div>
        </div>
        -->
        
      </section>

     
  <div class="clearfix"></div>
  
      <div class="thank-you">Thank You</div>
      
      <div class="clearfix"></div>
  

    <div class="footer-basic">
        <footer>
            <div class="social">
         
            <a href="#"><img src="http://res.cloudinary.com/genhub/image/upload/v1629724683/facebook-darkblue-01-facebook-icon-small-11563255210muq5fcu8vc_ihmupj.png" width="30px" height="30px"/></a>
            <a href="#"><img src="http://res.cloudinary.com/genhub/image/upload/v1629726054/303-3035037_line-clipart-computer-icons-social-media-facebook-small_t6qwvu.jpg" width="30px" height="30px"/></a></a>
            <a href="#"><img src="http://res.cloudinary.com/genhub/image/upload/v1629726151/200-2006750_twitter-linkedin-youtube-instagram-xing-linked-in-logo-rund_hl3cmt.jpg" width="30px" height="30px"/></a></a>
            
          
            <p class="copyright">dampharm © ${.now?string('yyyy')}</p>
        </footer>
    </div>
    </div>

  </body>
</html>



<#function itemTotalAfterDiscount item> <#local
	amount=item.quantity*item.product.price> <#if
	item.discount !=0> <#local
	discount=amount-((amount*item.discount)/100)> <#local
	amount=discount></#if> <#return amount></#function>

<#function itemSubtotal invoice> <#local totalSum=0>
<#list invoice.items as item> <#local
	totalSum +=itemTotalAfterDiscount(item)></#list> <#return totalSum></#function>
