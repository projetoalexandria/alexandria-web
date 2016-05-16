(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('GenderController', GenderController);

    GenderController.$inject = ['$scope', '$state', 'Gender'];

    function GenderController ($scope, $state, Gender) {
        var vm = this;
        vm.genders = [];
        vm.loadAll = function() {
            Gender.query(function(result) {
                vm.genders = result;
            });
        };

        vm.loadAll();
        
    }
})();
