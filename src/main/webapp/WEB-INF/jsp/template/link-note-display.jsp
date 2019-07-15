<div ng-repeat="linkNote in link.linkNotes" class="linknote">
    <div ng-class-odd="'linknote-header-left'" ng-class-even="'linknote-header-right'">
        <span class="linknote-header-name">
            {{linkNote.firstName}} {{linkNote.lastName}}
        </span>
        <span class="linknote-header-postTimestamp"
              ng-show="linkNote.postTimestampDisplay">
            {{linkNote.postTimestampDisplay}}
        </span>
    </div>
    <div class="linknote-body bubble" ng-class-odd="'me'" ng-class-even="'you'" >
        {{linkNote.note}}
    </div>
</div>
