(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('NeighborhoodDetailController', NeighborhoodDetailController);

    NeighborhoodDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Neighborhood', 'City'];

    function NeighborhoodDetailController($scope, $rootScope, $stateParams, entity, Neighborhood, City) {
        var vm = this;
        vm.neighborhood = entity;
        vm.load = function (id) {
            Neighborhood.get({id: id}, function(result) {
                vm.neighborhood = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:neighborhoodUpdate', function(event, result) {
            vm.neighborhood = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
