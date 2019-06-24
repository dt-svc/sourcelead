<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>	
<%-- <input type="hidden" id="invoiceTemplateId" value="${invoiceTemplate.id}"/> --%>
<c:forEach items="${invoiceTemplate.itemObjectList}" var="item">
	<c:choose>
		<c:when test="${item.get('type') == 'hidden'}">
			<li data-section="${item.get('type')}" data-position="${item.get('position')}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid sortable-hidden"></li>
		</c:when>
		<c:otherwise>
			<c:if test="${item.get('type') == 'image'}">
				<li data-name="${item.get('name')}" data-section="${item.get('type')}" data-position="${item.get('position')}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
					<span class="">${item.get('name')}</span>
						<c:forEach begin="0" end="${item.getJSONArray('subItems').length() -1}" var="index">
							<div class="cusInList">
								<input class="checkbox-custom" ${item.getJSONArray('subItems').getJSONObject(index).get('checked') ? "checked='checked'" : ""} type="checkbox">
								<img src="${item.getJSONArray('subItems').getJSONObject(index).get('value')}" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)" data-id="invoiceLogo" data-name="${item.getJSONArray('subItems').getJSONObject(index).get('imageName')}" data-style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}" style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">
							</div>
						</c:forEach>
					<span class="movIcon"></span>
				</li>
			</c:if>
			<c:if test="${item.get('type') == 'table'}">
				<li data-name="${item.get('name')}" data-section="${item.get('type')}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid" style="width: 98%;">
					<span class="">${item.get('name')}</span>
						<div class="project_tabs_inn_table">
							<table onclick="showTableSettingsTab(this)" id="invoiceTable" data-id="invoiceTable">
								<c:forEach begin="0" end="${item.getJSONArray('subItems').length() -1}" var="index">
									<c:if test="${item.getJSONArray('subItems').getJSONObject(index).get('value') == 'th'}">
										<thead data-style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">
											<tr>
												<th style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">Column 1</th>
												<th style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">Column 2</th>
												<th style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">Column 3</th>
												<th style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">...</th>
												<th style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">...</th>
												<th style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">Column n</th>
											</tr>
										</thead>
									</c:if>
									<c:if test="${item.getJSONArray('subItems').getJSONObject(index).get('value') == 'td'}">
										<tbody data-style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">
											<tr>
												<td style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">Value 1</td>
												<td style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">Value 2</td>
												<td style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">Value 3</td>
												<td style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">...</td>
												<td style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">...</td>
												<td style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">Value n</td>
											</tr>
										</tbody>
									</c:if>
								</c:forEach>
							</table>
						</div>
					<span class="movIcon"></span>
				</li>
			</c:if>
			<c:if test="${item.get('type') == 'text'}">
				<li data-name="${item.get('name')}" data-section="${item.get('type')}" data-position="${item.get('position')}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
					<span class="">${item.get('name')}</span>
						<c:forEach begin="0" end="${item.getJSONArray('subItems').length() -1}" var="index">
							<c:set value="text-align:left;" var="textStyle"></c:set>
							<c:forEach items="${item.getJSONArray('subItems').getJSONObject(index).get('style').split(';')}" var="s">
								<c:if test="${fn:contains(s, 'text-align')}">
									<c:set value="${s}" var="textStyle"></c:set>
								</c:if>
							</c:forEach>
							<div class="cusInList" style="${textStyle}">
								<input class="checkbox-custom" ${item.getJSONArray('subItems').getJSONObject(index).get('checked') ? "checked='checked'" : ""} type="checkbox">
								<label onclick="showTextSettingsTab(this)" data-style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}" style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">${item.getJSONArray('subItems').getJSONObject(index).get('value')}</label>
							</div>
						</c:forEach>
					<span class="movIcon"></span>
				</li>
			</c:if>
			<c:if test="${item.get('type') == 'footer'}">
				<li data-name="${item.get('name')}" data-section="${item.get('type')}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid disable-drag" style="width: 98%;">
					<span class="">${item.get('name')}</span>
						<c:forEach begin="0" end="${item.getJSONArray('subItems').length() -1}" var="index">
							<c:set value="text-align:left;" var="textStyle"></c:set>
							<c:forEach items="${item.getJSONArray('subItems').getJSONObject(index).get('style').split(';')}" var="s">
								<c:if test="${fn:contains(s, 'text-align')}">
									<c:set value="${s}" var="textStyle"></c:set>
								</c:if>
							</c:forEach>
							<div class="cusInList" style="${textStyle}">
								<input class="checkbox-custom" ${item.getJSONArray('subItems').getJSONObject(index).get('checked') ? "checked='checked'" : ""} type="checkbox">
								<label onclick="showTextSettingsTab(this)" data-style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}" style="${item.getJSONArray('subItems').getJSONObject(index).get('style')}">${item.getJSONArray('subItems').getJSONObject(index).get('value')}</label>
							</div>
						</c:forEach>
					<!-- <span class="movIcon"></span> -->
				</li>
			</c:if>
		</c:otherwise>
	</c:choose>
</c:forEach>
