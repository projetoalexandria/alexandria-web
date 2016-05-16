(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('StateDeleteController',StateDeleteController);

    StateDeleteController.$inject = ['$uibModalInstance', 'entity', 'State'];

    function StateDeleteController($uibModalInstance, entity, State) {
        var vm = this;
        vm.state = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            State.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
