(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AcquisitionTypeDialogController', AcquisitionTypeDialogController);

    AcquisitionTypeDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'AcquisitionType'];

    function AcquisitionTypeDialogController ($scope, $stateParams, $uibModalInstance, entity, AcquisitionType) {
        var vm = this;
        vm.acquisitionType = entity;
        vm.load = function(id) {
            AcquisitionType.get({id : id}, function(result) {
                vm.acquisitionType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:acquisitionTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.acquisitionType.id !== null) {
                AcquisitionType.update(vm.acquisitionType, onSaveSuccess, onSaveError);
            } else {
                AcquisitionType.save(vm.acquisitionType, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
