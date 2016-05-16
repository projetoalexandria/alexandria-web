(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Book', 'AgeBracket', 'AcquisitionType', 'PublishingHouse', 'Gender', 'Author'];

    function BookDetailController($scope, $rootScope, $stateParams, entity, Book, AgeBracket, AcquisitionType, PublishingHouse, Gender, Author) {
        var vm = this;
        vm.book = entity;
        vm.load = function (id) {
            Book.get({id: id}, function(result) {
                vm.book = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:bookUpdate', function(event, result) {
            vm.book = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
