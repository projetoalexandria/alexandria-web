(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AgeBracketController', AgeBracketController);

    AgeBracketController.$inject = ['$scope', '$state', 'AgeBracket'];

    function AgeBracketController ($scope, $state, AgeBracket) {
        var vm = this;
        vm.ageBrackets = [];
        vm.loadAll = function() {
            AgeBracket.query(function(result) {
                vm.ageBrackets = result;
            });
        };

        vm.loadAll();
        
    }
})();
