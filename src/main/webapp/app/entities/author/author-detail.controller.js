(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('AuthorDetailController', AuthorDetailController);

    AuthorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Author'];

    function AuthorDetailController($scope, $rootScope, $stateParams, entity, Author) {
        var vm = this;
        vm.author = entity;
        vm.load = function (id) {
            Author.get({id: id}, function(result) {
                vm.author = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:authorUpdate', function(event, result) {
            vm.author = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
