<div class="modal-header">
    <h3>${designer_pageInfo_pageGuests}</h3>
</div>
<div class="modal-body">
    <div ng-show="newPageGuestUsernameSuccess" class="alert alert-success">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        {{newPageGuestUsernameSuccess}}
    </div>
    <div ng-show="newPageGuestUsernameErrors" class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        {{newPageGuestUsernameErrors}}
    </div>
    <div ng-show="violations.newPageGuestUsernameRequired" class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        ${designer_pageGuests_validate_required}
    </div>
    <div ng-show="violations.newPageGuestUsernameLength" class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        ${account_username_validate_length}
    </div>
    <div class="row-fluid">
        <form name="addGuestForm" ng-submit="addGuest()" novalidate class="pageGuestForm">
            <div class="span4">
                ${designer_pageGuests_addGuestByUsername}
            </div>
            <div class="span5">
                <input
                    name="newPageGuestUsername"
                    type="text"
                    required
                    ng-model="newPageGuestUsername"
                    ng-minlength="${account_username_spec_minSize}"
                    ng-maxlength="${account_username_spec_maxSize}"
                    />
            </div>
            <div class="span3">
                <button type="submit"
                        class="btn pageGuestsAddGuestBtn"
                        ng-disabled="addGuestForm.$invalid || isAddGuestFormPartiallyPristine()"
                        >
                    ${designer_pageGuests_addGuest}
                </button>
            </div>
        </form>
    </div>
    <br />
    <div class="pageGuestGridHeader">
        <span class="pageGuestGridColumnUsername">${account_username}</span>
        <span class="pageGuestGridColumnFirstName">${account_firstName}</span>
        <span class="pageGuestGridColumnLastName">${account_lastName}</span>
        <span class="pageGuestGridColumnCanModify">${designer_pageGuests_canModify}</span>
        <span class="pageGuestGridColumnDelete pageGuestGridColumnDeleteHeader">${delete}</span>
    </div>
    <div class="pageGuestGrid">
        <div ng-repeat="pageGuest in pageData.pageGuests">
            <span class="pageGuestGridColumnUsername">{{pageGuest.username}}</span>
            <span class="pageGuestGridColumnFirstName">{{pageGuest.firstName}}</span>
            <span class="pageGuestGridColumnLastName">{{pageGuest.lastName}}</span>
            <span class="pageGuestGridColumnCanModify pageGuestGridColumnCanModifyCell">
                <input type="checkbox" class="pageGuestGridColumnCanModify" ng-model="pageGuest.canModify" />
            </span>
            <span class="pageGuestGridColumnDelete">
                <button class="close pageGuestGridColumnDelete" ng-click="deleteGuest(pageGuest)">&times;</button>
            </span>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button class="btn pageGuestCloseBtn" ng-click="close()">${button_close}</button>
</div>
