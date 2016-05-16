'use strict';

describe('Controller Tests', function() {

    describe('Book Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBook, MockAgeBracket, MockAcquisitionType, MockPublishingHouse, MockGender, MockAuthor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBook = jasmine.createSpy('MockBook');
            MockAgeBracket = jasmine.createSpy('MockAgeBracket');
            MockAcquisitionType = jasmine.createSpy('MockAcquisitionType');
            MockPublishingHouse = jasmine.createSpy('MockPublishingHouse');
            MockGender = jasmine.createSpy('MockGender');
            MockAuthor = jasmine.createSpy('MockAuthor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Book': MockBook,
                'AgeBracket': MockAgeBracket,
                'AcquisitionType': MockAcquisitionType,
                'PublishingHouse': MockPublishingHouse,
                'Gender': MockGender,
                'Author': MockAuthor
            };
            createController = function() {
                $injector.get('$controller')("BookDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'alexandriaApp:bookUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
