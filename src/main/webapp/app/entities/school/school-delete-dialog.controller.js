(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('SchoolDeleteController',SchoolDeleteController);

    SchoolDeleteController.$inject = ['$uibModalInstance', 'entity', 'School'];

    function SchoolDeleteController($uibModalInstance, entity, School) {
        var vm = this;
        vm.school = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            School.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
