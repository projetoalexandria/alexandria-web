(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('SchoolController', SchoolController);

    SchoolController.$inject = ['$scope', '$state', 'School'];

    function SchoolController ($scope, $state, School) {
        var vm = this;
        vm.schools = [];
        vm.loadAll = function() {
            School.query(function(result) {
                vm.schools = result;
            });
        };

        vm.loadAll();
        
    }
})();
