(function() {
    'use strict';
    angular
        .module('alexandriaApp')
        .factory('Neighborhood', Neighborhood);

    Neighborhood.$inject = ['$resource'];

    function Neighborhood ($resource) {
        var resourceUrl =  'api/neighborhoods/:id';

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
