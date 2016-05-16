(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('PublishingHouseDialogController', PublishingHouseDialogController);

    PublishingHouseDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PublishingHouse', 'Cep'];

    function PublishingHouseDialogController ($scope, $stateParams, $uibModalInstance, entity, PublishingHouse, Cep) {
        var vm = this;
        vm.publishingHouse = entity;
        vm.ceps = Cep.query();
        vm.load = function(id) {
            PublishingHouse.get({id : id}, function(result) {
                vm.publishingHouse = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:publishingHouseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.publishingHouse.id !== null) {
                PublishingHouse.update(vm.publishingHouse, onSaveSuccess, onSaveError);
            } else {
                PublishingHouse.save(vm.publishingHouse, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
