(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('StudentController', StudentController);

    StudentController.$inject = ['$scope', '$state', 'Student'];

    function StudentController ($scope, $state, Student) {
        var vm = this;
        vm.students = [];
        vm.loadAll = function() {
            Student.query(function(result) {
                vm.students = result;
            });
        };

        vm.loadAll();
        
    }
})();
