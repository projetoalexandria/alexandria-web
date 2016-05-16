(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('CepController', CepController);

    CepController.$inject = ['$scope', '$state', 'Cep'];

    function CepController ($scope, $state, Cep) {
        var vm = this;
        vm.ceps = [];
        vm.loadAll = function() {
            Cep.query(function(result) {
                vm.ceps = result;
            });
        };

        vm.loadAll();
        
    }
})();
