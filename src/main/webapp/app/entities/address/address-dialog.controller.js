(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AddressDialogController', AddressDialogController);

    AddressDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Address', 'Neighborhood'];

    function AddressDialogController ($scope, $stateParams, $uibModalInstance, entity, Address, Neighborhood) {
        var vm = this;
        vm.address = entity;
        vm.neighborhoods = Neighborhood.query();
        vm.load = function(id) {
            Address.get({id : id}, function(result) {
                vm.address = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:addressUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.address.id !== null) {
                Address.update(vm.address, onSaveSuccess, onSaveError);
            } else {
                Address.save(vm.address, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
