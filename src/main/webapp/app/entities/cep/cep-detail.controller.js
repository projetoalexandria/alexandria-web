(function() {
    'use strict';

    angular
        .module('alexandriaApp')
        .controller('CepDetailController', CepDetailController);

    CepDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Cep', 'Address'];

    function CepDetailController($scope, $rootScope, $stateParams, entity, Cep, Address) {
        var vm = this;
        vm.cep = entity;
        vm.load = function (id) {
            Cep.get({id: id}, function(result) {
                vm.cep = result;
            });
        };
        var unsubscribe = $rootScope.$on('alexandriaApp:cepUpdate', function(event, result) {
            vm.cep = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
