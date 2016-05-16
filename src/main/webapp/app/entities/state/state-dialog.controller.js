(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('StateDialogController', StateDialogController);

    StateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'State', 'Country'];

    function StateDialogController ($scope, $stateParams, $uibModalInstance, entity, State, Country) {
        var vm = this;
        vm.state = entity;
        vm.countrys = Country.query();
        vm.load = function(id) {
            State.get({id : id}, function(result) {
                vm.state = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:stateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.state.id !== null) {
                State.update(vm.state, onSaveSuccess, onSaveError);
            } else {
                State.save(vm.state, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
