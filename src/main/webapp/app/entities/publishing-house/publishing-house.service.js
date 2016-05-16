(function() {
    'use strict';
    angular
        .module('alexandriaApp')
        .factory('PublishingHouse', PublishingHouse);

    PublishingHouse.$inject = ['$resource'];

    function PublishingHouse ($resource) {
        var resourceUrl =  'api/publishing-houses/:id';

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
