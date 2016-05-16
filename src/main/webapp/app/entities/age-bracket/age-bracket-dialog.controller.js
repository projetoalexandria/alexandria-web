(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AgeBracketDialogController', AgeBracketDialogController);

    AgeBracketDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'AgeBracket'];

    function AgeBracketDialogController ($scope, $stateParams, $uibModalInstance, entity, AgeBracket) {
        var vm = this;
        vm.ageBracket = entity;
        vm.load = function(id) {
            AgeBracket.get({id : id}, function(result) {
                vm.ageBracket = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:ageBracketUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.ageBracket.id !== null) {
                AgeBracket.update(vm.ageBracket, onSaveSuccess, onSaveError);
            } else {
                AgeBracket.save(vm.ageBracket, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
