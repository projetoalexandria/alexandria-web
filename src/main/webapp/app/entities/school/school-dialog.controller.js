(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('SchoolDialogController', SchoolDialogController);

    SchoolDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'School', 'Cep', 'User'];

    function SchoolDialogController ($scope, $stateParams, $uibModalInstance, entity, School, Cep, User) {
        var vm = this;
        vm.school = entity;
        vm.ceps = Cep.query();
        vm.users = User.query();
        vm.load = function(id) {
            School.get({id : id}, function(result) {
                vm.school = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('alexandriaApp:schoolUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.school.id !== null) {
                School.update(vm.school, onSaveSuccess, onSaveError);
            } else {
                School.save(vm.school, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
