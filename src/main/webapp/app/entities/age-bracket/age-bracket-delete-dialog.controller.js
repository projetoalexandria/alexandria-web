(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AgeBracketDeleteController',AgeBracketDeleteController);

    AgeBracketDeleteController.$inject = ['$uibModalInstance', 'entity', 'AgeBracket'];

    function AgeBracketDeleteController($uibModalInstance, entity, AgeBracket) {
        var vm = this;
        vm.ageBracket = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            AgeBracket.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
