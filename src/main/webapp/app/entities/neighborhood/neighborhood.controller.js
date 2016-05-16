(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('NeighborhoodController', NeighborhoodController);

    NeighborhoodController.$inject = ['$scope', '$state', 'Neighborhood'];

    function NeighborhoodController ($scope, $state, Neighborhood) {
        var vm = this;
        vm.neighborhoods = [];
        vm.loadAll = function() {
            Neighborhood.query(function(result) {
                vm.neighborhoods = result;
            });
        };

        vm.loadAll();
        
    }
})();
