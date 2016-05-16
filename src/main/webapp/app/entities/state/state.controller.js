(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('StateController', StateController);

    StateController.$inject = ['$scope', '$state', 'State'];

    function StateController ($scope, $state, State) {
        var vm = this;
        vm.states = [];
        vm.loadAll = function() {
            State.query(function(result) {
                vm.states = result;
            });
        };

        vm.loadAll();
        
    }
})();
