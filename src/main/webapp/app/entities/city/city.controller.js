(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('CityController', CityController);

    CityController.$inject = ['$scope', '$state', 'City'];

    function CityController ($scope, $state, City) {
        var vm = this;
        vm.cities = [];
        vm.loadAll = function() {
            City.query(function(result) {
                vm.cities = result;
            });
        };

        vm.loadAll();
        
    }
})();
