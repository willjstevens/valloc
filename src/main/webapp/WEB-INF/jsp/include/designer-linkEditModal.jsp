<span ng-controller="EditLinkController" class="linkContent">
    {{link.name}}
    <div modal="shouldBeOpen" close="close()" options="opts">
        <div class="modal-header">
            <h3>${designer_editLink_title}</h3>
        </div>
        <div class="modal-body">
            <div ng-class="formSupport.displayErrorSectionNEW()">
                <div ng-show="violations.linkNameRequired">${designer_linkName_validate_required}</div>
                <div ng-show="violations.linkNameLength">${designer_linkName_validate_length}</div>
                <div ng-show="violations.linkUrlRequired">${designer_linkUrl_validate_required}</div>
                <div ng-show="violations.linkUrlLength">${designer_linkUrl_validate_length}</div>
            </div>
            <form name="editLinkForm" class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="linkText">${designer_pageInfo_linkText}</label>
                    <div class="controls">
                        <input id="linkText"
                               name="linkText"
                               type="text"
                               class="input-xlarge"
                               required
                               ng-model="linkText"
                               ng-minlength="${designer_linkName_spec_minSize}"
                               ng-maxlength="${designer_linkName_spec_maxSize}"
                                >
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="linkUrl">${designer_pageInfo_linkUrl}</label>
                    <div class="controls">
                        <input id="linkUrl"
                               name="linkUrl"
                               type="text"
                               class="input-xlarge"
                               required
                               ng-model="linkUrl"
                               ng-minlength="${designer_linkUrl_spec_minSize}"
                               ng-maxlength="${designer_linkUrl_spec_maxSize}"
                                >
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="linkNote">${designer_linkNote_addNote}</label>
                    <div class="controls">
                        <textarea id="linkNote"
                               name="linkNote"
                               type="text"
                               rows="7"
                               class="input-xlarge"
                               ng-model="linkNote">
                        </textarea>
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary"
                    ng-click="save()"
                    ng-disabled="editLinkForm.$invalid"
                    >
                ${button_save}
            </button>
            <button class="btn" ng-click="close()">${button_cancel}</button>
        </div>
    </div>
    <button class="close deleteIcon" ng-click="deleteLink()">&times;</button>
    <a ng-click="open()" class="link-decorator-icon"><span class="icon-edit pull-right"></span></a>


    <span class="dropdown-new" ng-show="link.linkNotes.length > 0">
        <a class="dropdown-toggle link-decorator-icon">
            <i class="icon-comment"></i>
        </a>
        <ul class="dropdown-content">
            <link-note-display />
        </ul>
    </span>
</span>