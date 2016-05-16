(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('CountryDialogController', CountryDialogController);

    CountryDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Country'];

    function CountryDialogController ($scope, $stateParams, $uibModalInstance, entity, Country) {
        var vm = this;
        vm.country = entity;
        vm.load = function(id) {
            Country.get({id : id}, function(result) {
                vm.country = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:countryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.country.id !== null) {
                Country.update(vm.country, onSaveSuccess, onSaveError);
            } else {
                Country.save(vm.country, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
