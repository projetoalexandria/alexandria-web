(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('NeighborhoodDialogController', NeighborhoodDialogController);

    NeighborhoodDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Neighborhood', 'City'];

    function NeighborhoodDialogController ($scope, $stateParams, $uibModalInstance, entity, Neighborhood, City) {
        var vm = this;
        vm.neighborhood = entity;
        vm.citys = City.query();
        vm.load = function(id) {
            Neighborhood.get({id : id}, function(result) {
                vm.neighborhood = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:neighborhoodUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.neighborhood.id !== null) {
                Neighborhood.update(vm.neighborhood, onSaveSuccess, onSaveError);
            } else {
                Neighborhood.save(vm.neighborhood, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
