<div class="sectionTitle">
    <span ng-controller="EditSectionController">
        {{section.name}}
        <div modal="shouldBeOpen" close="close()" options="opts">
            <div class="modal-header">
                <h3>${designer_editSection_title}</h3>
            </div>
            <div class="modal-body">
                <div ng-class="formSupport.displayErrorSectionNEW()">
                    <div ng-show="violations.sectionNameRequired">${designer_sectionName_validate_required}</div>
                    <div ng-show="violations.sectionNameLength">${designer_sectionName_validate_length}</div>
                </div>
                <form name="editSectionForm" class="form-horizontal">
                    <div class="control-group">
                        <label class="control-label" for="sectionTitle">${designer_pageInfo_sectionTitle}</label>
                        <div class="controls">
                            <input id="sectionTitle"
                                   name="sectionTitle"
                                   type="text"
                                   class="input-xlarge"
                                   required
                                   ng-model="sectionTitle"
                                   ng-minlength="${designer_sectionName_spec_minSize}"
                                   ng-maxlength="${designer_sectionName_spec_maxSize}"
                                    >
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary"
                        ng-click="save()"
                        ng-disabled="editSectionForm.$invalid"
                        >
                    ${button_save}
                </button>
                <button class="btn" ng-click="close()">${button_cancel}</button>
            </div>
        </div>
        <button class="close deleteIcon sectionDeleteIcon" ng-click="deleteSection(section.name)">&times;</button>
        <a ng-click="open()" class="link-decorator-icon"><span class="icon-edit pull-right"></span></a>
    </span>
</div>