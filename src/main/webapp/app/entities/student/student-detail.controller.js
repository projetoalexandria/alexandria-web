(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('StudentDetailController', StudentDetailController);

    StudentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Student', 'Cep'];

    function StudentDetailController($scope, $rootScope, $stateParams, entity, Student, Cep) {
        var vm = this;
        vm.student = entity;
        vm.load = function (id) {
            Student.get({id: id}, function(result) {
                vm.student = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
