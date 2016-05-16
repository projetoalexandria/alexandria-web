(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('GenderDeleteController',GenderDeleteController);

    GenderDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gender'];

    function GenderDeleteController($uibModalInstance, entity, Gender) {
        var vm = this;
        vm.gender = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Gender.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
