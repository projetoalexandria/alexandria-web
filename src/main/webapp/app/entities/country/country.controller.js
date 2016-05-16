(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('CountryController', CountryController);

    CountryController.$inject = ['$scope', '$state', 'Country'];

    function CountryController ($scope, $state, Country) {
        var vm = this;
        vm.countries = [];
        vm.loadAll = function() {
            Country.query(function(result) {
                vm.countries = result;
            });
        };

        vm.loadAll();
        
    }
})();
