(function() {
    'use strict';
    angular
        .module('alexandriaApp')
        .factory('Cep', Cep);

    Cep.$inject = ['$resource'];

    function Cep ($resource) {
        var resourceUrl =  'api/ceps/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
