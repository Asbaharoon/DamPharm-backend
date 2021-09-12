<!DOCTYPE html>
<html lang="ar">
<head>
<meta charset="utf-8">
<title>Io (third)</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta name="description" content="Invoicebus Invoice Template">
<meta name="author" content="Invoicebus">

<meta name="template-hash" content="f3142bbb0a1696d5caa932ecab0fc530">

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
	font-family: AlinmaTheSans-Plain, "Trebuchet MS", Helvetica, sans-serif;
	direction: rtl;
}

* {
	font-family: AlinmaTheSans-Plain, "Trebuchet MS", Helvetica,
		sans-serif !important;
}

html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p,
	blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn,
	em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var,
	b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend,
	table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas,
	details, embed, figure, figcaption, footer, header, hgroup, menu, nav,
	output, ruby, section, summary, time, mark, audio, video {
	margin: 0;
	padding: 0;
	border: 0;
	font: inherit;
	font-size: 100%;
	vertical-align: baseline;
}

html {
	line-height: 1;
}

ol, ul {
	list-style: none;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}

caption, th, td {
	text-align: left;
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

article, aside, details, figcaption, figure, footer, header, hgroup,
	main, menu, nav, section, summary {
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
	margin: 0 auto;
	padding: 50px 40px;
	min-height: 1058px;
}

#memo .company-name {
	background: #8BA09E;
	padding : 10px 20px;
	position: relative;
	margin-bottom: 15px;
	padding: 10px 20px;
}

#memo .company-name span {
	color: white;
	display: inline-block;
	min-width: 20px;
	font-size: 36px;
	font-weight: bold;
	line-height: 1em;
}

.left-arrow {
	float:left;
	background: #fcba03
		url("http://res.cloudinary.com/genhub/image/upload/v1629713813/arrows_ggnd5t.png")
		560px center no-repeat;
	background-size: 100px auto;
}

#memo .logo {
	float: left;
	clear: left;
	margin-left: 20px;
}

#memo .logo img {
	width: 110px;
	height: 100px;
}

#memo .company-info {
	float: right;
	font-size: 12px;
	color: #8b8b8b;
	text-align: right;
}

#memo .company-info div {
	margin-bottom: 3px;
	min-width: 20px;
}

#memo .company-info span {
	display: inline-block;
	min-width: 20px;
}

#memo:after {
	content: '';
	display: block;
	clear: both;
}

#invoice-info {
	float: left;
	margin: 25px 0 0 20px;
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

#client-info {
	float: right;
	margin: 5px 20px 0 0;
	min-width: 220px;
	text-align: right;
}

#client-info>div {
	margin-bottom: 3px;
	min-width: 20px;
}

#client-info span {
	display: block;
	min-width: 20px;
}

#invoice-title-number {
	text-align: center;
	margin: 20px 0;
}

#invoice-title-number span {
	display: inline-block;
	min-width: 20px;
}

#invoice-title-number #title {
	margin-right: 15px;
	text-align: right;
	font-size: 20px;
	font-weight: bold;
}

#invoice-title-number #number {
	font-size: 15px;
	text-align: left;
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
	margin: 20px 0 35px 0;
}

#items table {
	direction: rtl;
	width: 100%;
	border-collapse: separate;
}

#items table th {
	padding: 12px 10px;
	text-align: right;
	background: #E6E7E7;
	border-bottom: 4px solid #487774;
}

#items table td {
	padding: 15px 10px;
	text-align: right;
	border-right: 1px solid #CCCCCF;
}

.currency {
	border-bottom: 4px solid #487774;
	padding: 0 25px;
}

.currency span {
	font-size: 11px;
	font-style: italic;
	color: #8b8b8b;
	display: inline-block;
	min-width: 20px;
}

#sums {
	float: left;
	background: #8BA09E url("../img/left-arrow.png") -17px bottom no-repeat;
	background-size: auto 100px;
	color: white;
}

#sums table tr th, #sums table tr td {
	min-width: 100px;
	padding: 8px 20px 8px 35px;
	text-align: left;
	font-weight: 600;
}

#sums table tr th {
	text-align: right;
	padding-right: 25px;
}

#sums table tr.amount-total th {
	text-transform: uppercase;
}

#sums table tr.amount-total th, #sums table tr.amount-total td {
	font-size: 16px;
	font-weight: bold;
}

#sums table tr:last-child th {
	text-transform: uppercase;
}

#sums table tr:last-child th, #sums table tr:last-child td {
	font-size: 16px;
	font-weight: bold;
	padding-top: 20px !important;
	padding-bottom: 40px !important;
}

#terms {
	margin: 50px 20px 10px 20px;
}

#terms>span {
	display: inline-block;
	min-width: 20px;
	font-weight: bold;
}

#terms>div {
	margin-top: 10px;
	min-height: 50px;
	min-width: 50px;
}

.payment-info {
	margin: 0 20px;
}

.payment-info div {
	font-size: 12px;
	color: #8b8b8b;
	display: inline-block;
	min-width: 20px;
}

.ib_bottom_row_commands {
	margin: 10px 0 0 20px !important;
}

.ibcl_tax_value:before {
	color: white !important;
}

@media print {
	#container {
		font-size: 11px !important;
		overflow: hidden !important;
	}
	#container footer {
		position: fixed;
		bottom: 10px;
	}
}
</style>
</head>
<body>
	<div id="container">
		<section id="memo">
			<div class="company-name">
				<span>${currentUser.companyName?trim}</span>
				<div class="left-arrow"></div>
			</div>

			<div class="logo">
				<img src='${currentUser.companyLogo?trim}' />
			</div>

			<div class="company-info">
				<div>
					<span>${currentUser.address?trim}</span> <span>{company_city_zip_state}</span>
				</div>
				<div>${currentUser.email?trim}</div>
				<div>${currentUser.phone?trim}</div>
			</div>
		</section>




		<section id="invoice-info">
			<div>
				<span>${invoice.createdAt?string('dd-MM-yyyy')}</span> <span>--------------</span>
			</div>

			<div>
				<span>تاريخ الفاتورة</span> <span>تاريخ الدفع</span>
			</div>
		</section>

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
				<a href="mailto:${invoice.customer.email?trim}">${invoice.customer.email?trim}</a>
			</div>
			</#if>


		</section>



		<div class="clearfix"></div>

		<section id="invoice-title-number">

			<span id="title">رقم الفاتورة</span> <span id="number">${invoice.id}</span>

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
						<h3>${item.product.name}</h3>
					</td>
					<td width="10">${item?index + 1}</td>
				</tr>
				</#list>
			</table>

		</section>

		<div class="currency">
			<span>العملة</span> <span>جنية مصرى</span>
		</div>

		<section id="sums">

			<table cellpadding="0" cellspacing="0">
				<tr>
					<td>${itemSubtotal(invoice)}</td>
					<th>المجموع الفرعي</th>
				</tr>

				<tr>
					<td></td>
					<th>ضريبة</th>
				</tr>

				<tr class="amount-total">
					<td>${itemSubtotal(invoice)}</td>
					<th>المجموع الإجمالي</th>
				</tr>
			</table>

		</section>

		<div class="clearfix"></div>

		<section id="terms">

			<span>ملحوظة</span>
			<div class="notice">
				<ul>
					<li>إجمالي الدفعة المستحقة خلال 30 يومًا</li>
					<li>يرجى تضمين رقم الفاتورة على الشيك الخاص بك</li>
				</ul>
			</div>

		</section>

		<div class="payment-info">
			<div>سجل تجارى</div>
			<div>{payment_info2}</div>

		</div>

		<footer> Invoice was created on a computer and is valid
			without the signature and seal. </footer>
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