(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('GenderDialogController', GenderDialogController);

    GenderDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gender'];

    function GenderDialogController ($scope, $stateParams, $uibModalInstance, entity, Gender) {
        var vm = this;
        vm.gender = entity;
        vm.load = function(id) {
            Gender.get({id : id}, function(result) {
                vm.gender = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:genderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.gender.id !== null) {
                Gender.update(vm.gender, onSaveSuccess, onSaveError);
            } else {
                Gender.save(vm.gender, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
